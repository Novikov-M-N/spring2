package com.github.novikovmn.spring2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class EurekaClientService {
    @Autowired
    private DiscoveryClient discoveryClient;
    @Value("false")
    private boolean moneyIsHere;
    private final String ANSWER_YES = "Держи, потом отдашь";
    private final String ANSWER_NO ="Извини, брат, сам пустой";
    private final String ANSWER_THANKS = "Спасибо. Отдам, как смогу";

    @Scheduled(fixedRate = 3000L)
    private void AskForLoan() {
        if (moneyIsHere) { return; }    // Если деньги есть, взаймы не просим.
        List<String> urls = new ArrayList<>();
        List<String> serviceIds = this.discoveryClient.getServices();
        if (serviceIds.size() == 1) { return; }   // Если в эфире кто-то один, то это Зуул. Он денег не даст.
        for (String serviceId : serviceIds) {
            List<ServiceInstance> instances = this.discoveryClient.getInstances(serviceId);
            for (ServiceInstance instance : instances) {
                if (instance.getPort() != 8999) {   // Костыль, чтобы наш сервис не пытался занимать денег у Зуула
                    urls.add("http://" + instance.getHost() + ":" + instance.getPort());
                }
            }
        }
        Random random = new Random();
        int index = random.nextInt(urls.size());
        String address = urls.get(index) + "/zaymi-deneg";
        try{
            URL url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            String response = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
            if (response.equals(ANSWER_YES)) {
                moneyIsHere = true;
                System.out.println("Получилось взять взаймы");
            } else {
                System.out.println("Не получилось взять взаймы: у соседа тоже нет денег");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Я попросил взаймы: " + address);
    }

    public String giveForLoan() {
        if (moneyIsHere) {
            moneyIsHere = false;
            System.out.println("Я дал взаймы");
            return ANSWER_YES;
        } else {
            System.out.println("Я не смог дать взаймы потому, что у самого нет денег");
            return ANSWER_NO;
        }
    }

    public String takeMoney() {
        moneyIsHere = true;
        return ANSWER_THANKS;
    }
}
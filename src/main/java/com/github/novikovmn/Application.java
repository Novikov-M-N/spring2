package com.github.novikovmn;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.novikovmn.payload.Address;
import com.github.novikovmn.payload.Payment;
import com.github.novikovmn.payload.Subscriber;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;

@SpringBootApplication
@EnableScheduling
public class Application {
    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Queue myQueue() {
        return new Queue("TEST12345", true);
    }

    @RabbitListener(queues = "TEST12345")
    public void list(String text) {
        System.out.println("Message from my queue: " + text);
        StringReader reader = new StringReader(text);
        ObjectMapper mapper = new ObjectMapper();
        try {
            Subscriber subscriber = mapper.readValue(reader, Subscriber.class);
            System.out.println("Получен объект:");
            System.out.println(subscriber.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(fixedRate = 5000L)
    private void SendMessage() {
        Subscriber subscriber = new Subscriber(
                "Иванов Иван"
                , new Address("Петухова", "95/1", 128)
                , 9139131313L);
        subscriber.addPayment(new Payment("10.11.2020", new BigDecimal(470)));
        subscriber.addPayment(new Payment("05.12.2020", new BigDecimal(320)));
        System.out.println("Отправляем объект:");
        System.out.println(subscriber.toString());

        ObjectMapper mapper = new ObjectMapper();
        StringWriter writer = new StringWriter();
        try {
            mapper.writeValue(writer, subscriber);
        } catch (IOException e) {
            e.printStackTrace();
        }
        rabbitTemplate.convertAndSend("example-2", "", writer.toString());
    }


}

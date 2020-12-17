package com.github.novikovmn.spring2;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class SequenceApplication {
    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("0")
    private Integer counter;

    public static void main(String[] args) {
        SpringApplication.run(SequenceApplication.class, args);
    }

    @Bean
    public Queue myQueue() {
        return new Queue("TEST12345", true);
    }

    @RabbitListener(queues = "TEST12345")
    public void list(String text) {
        Integer value = Integer.valueOf(text);
        StringBuilder sb = new StringBuilder();
        if (value % 3 == 0) { sb.append(3); }
        if (value % 5 == 0) { sb.append(5); }
        if (value % 3 != 0 && value % 5 != 0) { sb.append(value); }
        System.out.println("Message from my queue: " + sb.toString());
    }

    @Scheduled(fixedRate = 500L)
    private void SendMessage() {
        counter++;
        rabbitTemplate.convertAndSend("example-2", "", counter.toString());
        if (counter == 100) { counter = 0; }
    }
}

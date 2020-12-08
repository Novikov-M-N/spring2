package com.github.novikovmn.spring2.config;

import com.github.novikovmn.spring2.domain.Dto;
import com.github.novikovmn.spring2.domain.PayloadDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Autowired
    private SimpMessagingTemplate template;

    @Scheduled(fixedRate = 3000L)
    private void sendItem() {
        template.convertAndSend("/topic", "test message");
        template.convertAndSend("/topic", new PayloadDTO(1, "hello"));
    }

}

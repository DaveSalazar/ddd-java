package com.application.apps.administration.listeners;

import com.application.shared.domain.Service;
import com.application.shared.infrastructure.bus.event.rabbitmq.RabbitMqDomainEventsConsumer;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

@Service
public class RabbitMqConsumerAutoStarter implements ApplicationListener {

    private final RabbitMqDomainEventsConsumer rabbitMqDomainEventsConsumer;


    public RabbitMqConsumerAutoStarter(RabbitMqDomainEventsConsumer rabbitMqDomainEventsConsumer) {
        this.rabbitMqDomainEventsConsumer = rabbitMqDomainEventsConsumer;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if(event instanceof ApplicationReadyEvent) {
            rabbitMqDomainEventsConsumer.consume();
            System.out.println("RabbitMQ consumer has been started.");
        }
    }
}

package com.application.administration.shared.infrastructure.persistence;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.application.shared.infrastructure.bus.event.mysql.MySqlEventBus;
import com.application.shared.infrastructure.bus.event.rabbitmq.RabbitMqEventBus;
import com.application.shared.infrastructure.bus.event.rabbitmq.RabbitMqPublisher;

@Configuration
public class AdministrationRabbitMqEventBusConfiguration {
    private final RabbitMqPublisher publisher;
    private final MySqlEventBus     failoverPublisher;

    public AdministrationRabbitMqEventBusConfiguration(
        RabbitMqPublisher publisher,
        @Qualifier("administrationMysqlEventBus") MySqlEventBus failoverPublisher
    ) {
        this.publisher         = publisher;
        this.failoverPublisher = failoverPublisher;
    }

    @Bean
    public RabbitMqEventBus administrationRabbitMqEventBus() {
        return new RabbitMqEventBus(publisher, failoverPublisher);
    }
}

package com.application.administration.shared.infrastructure.persistence;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.application.shared.infrastructure.bus.event.DomainEventsInformation;
import com.application.shared.infrastructure.bus.event.mysql.MySqlDomainEventsConsumer;
import com.application.shared.infrastructure.bus.event.mysql.MySqlEventBus;
import com.application.shared.infrastructure.bus.event.spring.SpringApplicationEventBus;

@Configuration
public class AdministrationMySqlEventBusConfiguration {
    private final SessionFactory            sessionFactory;
    private final DomainEventsInformation   domainEventsInformation;
    private final SpringApplicationEventBus bus;

    public AdministrationMySqlEventBusConfiguration(
        @Qualifier("administration-session_factory") SessionFactory sessionFactory,
        DomainEventsInformation domainEventsInformation,
        SpringApplicationEventBus bus
    ) {
        this.sessionFactory          = sessionFactory;
        this.domainEventsInformation = domainEventsInformation;
        this.bus                     = bus;
    }

    @Bean
    public MySqlEventBus administrationMysqlEventBus() {
        return new MySqlEventBus(sessionFactory);
    }

    @Bean
    public MySqlDomainEventsConsumer administrationMySqlDomainEventsConsumer() {
        return new MySqlDomainEventsConsumer(sessionFactory, domainEventsInformation, bus);
    }
}

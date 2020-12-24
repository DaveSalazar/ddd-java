package com.application.administration.shared.infrastructure.persistence;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.application.shared.infrastructure.config.Parameter;
import com.application.shared.infrastructure.config.ParameterNotExist;
import com.application.shared.infrastructure.hibernate.HibernateConfigurationFactory;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class AdministrationHibernateConfiguration {
    private final HibernateConfigurationFactory factory;
    private final Parameter                     config;
    private final String                        CONTEXT_NAME = "administration";

    public AdministrationHibernateConfiguration(HibernateConfigurationFactory factory, Parameter config) {
        this.factory = factory;
        this.config  = config;
    }

    @Bean("administration-transaction_manager")
    public PlatformTransactionManager hibernateTransactionManager() throws ParameterNotExist {
        return factory.hibernateTransactionManager(sessionFactory());
    }

    @Bean("administration-session_factory")
    public LocalSessionFactoryBean sessionFactory() throws ParameterNotExist {
        return factory.sessionFactory(CONTEXT_NAME, dataSource());
    }

    @Bean("administration-data_source")
    public DataSource dataSource() throws ParameterNotExist {
        return factory.dataSource(
            config.get("ADMINISTRATION_DATABASE_HOST"),
            config.getInt("ADMINISTRATION_DATABASE_PORT"),
            config.get("ADMINISTRATION_DATABASE_NAME"),
            config.get("ADMINISTRATION_DATABASE_USER"),
            config.get("ADMINISTRATION_DATABASE_PASSWORD")
        );
    }
}

package com.application.shared.infrastructure.hibernate;

import com.application.shared.domain.Service;
import com.application.shared.infrastructure.GradleProperties;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("ToArrayCallWithZeroLengthArrayArgument")
@Service
public final class HibernateConfigurationFactory {
    private final ResourcePatternResolver resourceResolver;

    public HibernateConfigurationFactory(ResourcePatternResolver resourceResolver) {
        System.out.println("Configuring hibernate");
        this.resourceResolver = resourceResolver;
    }

    public PlatformTransactionManager hibernateTransactionManager(LocalSessionFactoryBean sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory.getObject());

        return transactionManager;
    }

    public LocalSessionFactoryBean sessionFactory(String contextName, DataSource dataSource) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setHibernateProperties(hibernateProperties());

        List<Resource> mappingFiles = searchMappingFiles(contextName);

        sessionFactory.setMappingLocations(mappingFiles.toArray(new Resource[mappingFiles.size()]));

        return sessionFactory;
    }

    public DataSource dataSource(
        String host,
        Integer port,
        String databaseName,
        String username,
        String password
    ) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(
            String.format(
                "jdbc:postgresql://%s:%s/%s",
                host,
                port,
                databaseName
            )
        );
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }

    private List<Resource> searchMappingFiles(String contextName) {
        List<String> modules   = subdirectoriesFor(contextName);
        List<String> goodPaths = new ArrayList<>();

        for (String module : modules) {
            String[] files = mappingFilesIn(module + "/infrastructure/persistence/hibernate/");
            System.out.println("Module: " + module + " has " + files.length + " files");
            for (String file : files) {
                System.out.println("hibernate file: " + file);
                goodPaths.add(module + "/infrastructure/persistence/hibernate/" + file);
            }
        }

        return goodPaths.stream().map(FileSystemResource::new).collect(Collectors.toList());
    }

    private List<String> subdirectoriesFor(String contextName) {
        String projectName;
        try {
            GradleProperties properties = new GradleProperties();
            projectName = properties.getProperty("rootProject.name");
        } catch (IOException e) {
            projectName = "application";
            e.printStackTrace();
        }
        String path = "./src/" + contextName + "/main/com/" + projectName + "/" + contextName + "/";

        String[] files = new File(path).list((current, name) -> new File(current, name).isDirectory());

        if (null == files) {
            path  = "./main/com/application/" + contextName + "/";
            files = new File(path).list((current, name) -> new File(current, name).isDirectory());
        }

        if (null == files) {
            return Collections.emptyList();
        }

        String finalPath = path;

        return Arrays.stream(files).map(file -> finalPath + file).collect(Collectors.toList());
    }

    private String[] mappingFilesIn(String path) {
        String[] files = new File(path).list((current, name) -> new File(current, name).getName().contains(".hbm.xml"));

        if (null == files) {
            return new String[0];
        }

        return files;
    }

    private Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.put(AvailableSettings.HBM2DDL_AUTO, "update");
        hibernateProperties.put(AvailableSettings.SHOW_SQL, "false");
        hibernateProperties.put(AvailableSettings.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
        hibernateProperties.put(AvailableSettings.ISOLATION, String.valueOf(Connection.TRANSACTION_REPEATABLE_READ));

        return hibernateProperties;
    }
}

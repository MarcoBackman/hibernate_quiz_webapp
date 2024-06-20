package com.example.week3day13project.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@Getter
@Slf4j
public class HibernateConfig {

    DBProperty DBProperty;

    HibernateConfig(DBProperty DBProperty) {
        this.DBProperty = DBProperty;
    }

    @Bean
    protected LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("com.example.week3day13project.domain.hibernate");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        log.info("Connecting to URL: {}\n USER: {}", DBProperty.getUrl(), DBProperty.getUsername());
        dataSource.setDriverClassName(DBProperty.getDriver());
        dataSource.setUrl(DBProperty.getUrl());
        dataSource.setUsername(DBProperty.getUsername());
        dataSource.setPassword(DBProperty.getPassword());
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager hibernateTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

    private Properties hibernateProperties() {
        log.info("Setting hibernate properties");
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.show_sql", DBProperty.getShowsql());
        hibernateProperties.setProperty("hibernate.dialect", DBProperty.getDialect());
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", DBProperty.getHbm2ddl());
        return hibernateProperties;
    }
}

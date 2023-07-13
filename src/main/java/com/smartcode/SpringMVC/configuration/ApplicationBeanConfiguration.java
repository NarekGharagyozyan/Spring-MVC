package com.smartcode.SpringMVC.configuration;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.TransactionManager;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class ApplicationBeanConfiguration {
    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("testfortest891@gmail.com");
        mailSender.setPassword("iabxmmbebumtizqt");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return mailSender;
    }

    @Bean("dataSource")
    public SingleConnectionDataSource singleConnectionDataSource() {
        SingleConnectionDataSource singleConnectionDataSource = new SingleConnectionDataSource();
        singleConnectionDataSource.setUrl("jdbc:postgresql://localhost:5432/postgres");
        singleConnectionDataSource.setUsername("postgres");
        singleConnectionDataSource.setPassword("0710");
        singleConnectionDataSource.setDriverClassName("org.postgresql.Driver");
        singleConnectionDataSource.setSuppressClose(true);
        return singleConnectionDataSource;
    }

    @Bean("hibernateJpaVendorAdapter")
    public HibernateJpaVendorAdapter hibernateJpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean("entityManagerFactory")
    public FactoryBean<EntityManagerFactory> localContainerEntityManagerFactoryBean(SingleConnectionDataSource singleConnectionDataSource, HibernateJpaVendorAdapter hibernateJpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean localEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localEntityManagerFactoryBean.setDataSource(singleConnectionDataSource);
        localEntityManagerFactoryBean.setPackagesToScan("com.smartcode.SpringMVC.model");
        localEntityManagerFactoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter);

        Map<String, String> map = new HashMap<>();
        map.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL95Dialect");
        map.put("hibernate.show_sql", "true");
        map.put("hibernate.format_sql", "true");
        map.put("hibernate.hbm2ddl.auto", "update");
        localEntityManagerFactoryBean.setJpaPropertyMap(map);
        return localEntityManagerFactoryBean;
    }

    @Bean
    public TransactionManager transactionManager(EntityManagerFactory entityManagerFactory, SingleConnectionDataSource dataSource) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
        return jpaTransactionManager;
    }

}

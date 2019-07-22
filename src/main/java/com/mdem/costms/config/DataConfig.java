package com.mdem.costms.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import com.zaxxer.hikari.HikariDataSource;

import java.util.Properties;

@Configuration
@PropertySources({
        @PropertySource("classpath:database.properties"),
        @PropertySource("classpath:hibernate.properties")
})
public class DataConfig {

    private final Environment environment;

    @Autowired
    public DataConfig(Environment environment) {
        this.environment = environment;
    }

    /**
     * The Tomcat JDBC Connection Pool
     */

//    @Bean
//    public javax.sql.DataSource getDataSource() {
//
//        DataSource dataSource = new DataSource();
//
//        dataSource.setDriverClassName(environment.getProperty("jdbc.driverClassName"));
//        dataSource.setUrl(environment.getProperty("jdbc.url"));
//        dataSource.setUsername(environment.getProperty("jdbc.username"));
//        dataSource.setPassword(environment.getProperty("jdbc.password"));
//
//        return dataSource;
//    }

    /**
     * The HikariCP JDBC Connection Pool
     */

    @Bean
    public javax.sql.DataSource getDataSource() {

        HikariDataSource dataSource = new HikariDataSource();

        dataSource.setDriverClassName(environment.getProperty("jdbc.driverClassName"));
        dataSource.setJdbcUrl(environment.getProperty("jdbc.url"));
        dataSource.setUsername(environment.getProperty("jdbc.username"));
        dataSource.setPassword(environment.getProperty("jdbc.password"));

        return dataSource;
    }

    /**
     * The C3P0 JDBC Connection Pool
     */

//    @Bean
//    public javax.sql.DataSource getDataSource() throws PropertyVetoException {
//
//        ComboPooledDataSource dataSource = new ComboPooledDataSource();
//
//        dataSource.setDriverClass(environment.getProperty("jdbc.driverClassName"));
//        dataSource.setJdbcUrl(environment.getProperty("jdbc.url"));
//        dataSource.setUser(environment.getProperty("jdbc.username"));
//        dataSource.setPassword(environment.getProperty("jdbc.password"));
//
//        return dataSource;
//    }

    /**
     * The Spring JDBC Connection
     */

//    @Bean
//    public javax.sql.DataSource getDataSource() {
//
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//
//        dataSource.setDriverClassName(environment.getProperty("jdbc.driverClassName"));
//        dataSource.setUrl(environment.getProperty("jdbc.url"));
//        dataSource.setUsername(environment.getProperty("jdbc.username"));
//        dataSource.setPassword(environment.getProperty("jdbc.password"));
//
//        return dataSource;
//    }

    /**
     * Build session
     */

    @Autowired
    @Bean
    public LocalSessionFactoryBean getSessionFactory(javax.sql.DataSource dataSource) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);

        sessionFactory.setPackagesToScan("com.mdem.costms.model");
        sessionFactory.setHibernateProperties(getHibernateProperties());

        return sessionFactory;
    }

    @Autowired
    @Bean
    public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }

    private Properties getHibernateProperties() {
        Properties properties = new Properties();

        properties.setProperty("hibernate.dialect", environment.getProperty("hibernate.dialect"));
        properties.setProperty("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));

        return properties;
    }
}
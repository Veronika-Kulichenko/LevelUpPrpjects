package com.finance.bookkeeping.dao.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Veronika Kulichenko on 02.01.17.
 */
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "com.finance.bookkeeping.dao")
public class MySQLConfig {

  @Value("${jdbc.driverClassName}")
  private String driverClassName;
  @Value("${jdbc.url}")
  private String url;
  @Value("${jdbc.username}")
  private String username;
  @Value("${jdbc.password}")
  private String password;

  @Value("${hibernate.dialect}")
  private String hibernateDialect;
  @Value("${hibernate.show_sql}")
  private String hibernateShowSql;
  @Value("${hibernate.hbm2ddl.auto}")
  private String hibernateHbm2ddlAuto;

  private static final String ENTITY_PACKAGE = "com.finance.bookkeeping.core.entity";

  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource ds = new DriverManagerDataSource(url, username, password);
    ds.setDriverClassName(driverClassName);

    Properties properties = new Properties();
    properties.put("minPoolSize", "1");
    properties.put("maxPoolSize", "100");
    properties.put("breakAfterAcquireFailure", "false");
    properties.put("acquireRetryAttempts", "3");
    properties.put("idleConnectionTestPeriod", "300");
    properties.put("testConnectionOnCheckout", "true");
    ds.setConnectionProperties(properties);
    return ds;
  }

  @Bean
  public HibernateTransactionManager transactionManager() throws IOException {
    HibernateTransactionManager htm = new HibernateTransactionManager();
    htm.setSessionFactory(getSessionFactory());
    return htm;
  }

  @Bean
  public HibernateTemplate hibernateTemplate() throws IOException {
    return new HibernateTemplate(getSessionFactory());
  }

  @Bean
  public SessionFactory getSessionFactory() throws IOException {
    final LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
    sessionFactory.setDataSource(dataSource());
    sessionFactory.setPackagesToScan(ENTITY_PACKAGE);
    sessionFactory.setHibernateProperties(hibernateProperties());
    sessionFactory.afterPropertiesSet();
    return sessionFactory.getObject();
  }

  @Bean
  public Properties hibernateProperties() {
    Properties properties = new Properties();
    properties.put("hibernate.dialect", hibernateDialect);
    properties.put("hibernate.show_sql", hibernateShowSql);
    properties.put("hibernate.hbm2ddl.auto", hibernateHbm2ddlAuto);
    return properties;
  }

  @Bean
  public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
    return new PersistenceExceptionTranslationPostProcessor();
  }
}

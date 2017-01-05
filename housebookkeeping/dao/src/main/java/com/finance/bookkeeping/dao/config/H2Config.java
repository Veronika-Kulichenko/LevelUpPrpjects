package com.finance.bookkeeping.dao.config;

import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;
/**
 * @author Veronika Kulichenko on 02.01.17.
 */

/**
 * In memory H2-database config. Used only for test purposes
 */
@Configuration
@Profile("h2")
@EnableTransactionManagement
@PropertySource({"classpath:persistence.properties"})
@ComponentScan("com.finance.bookkeeping.dao")
public class H2Config {

  private static final String LAUNCH_SCRIPT = "create-database.sql";

  @Bean
  public LocalSessionFactoryBean sessionFactory() {
    LocalSessionFactoryBean result = new LocalSessionFactoryBean();
    result.setDataSource(dataSource());
    result.setPackagesToScan("com.finance.bookkeeping.core.entity");
    Properties properties = new Properties();
    properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
    result.setHibernateProperties(properties);
    return result;
  }

  @Bean
  public DataSource dataSource() {
    return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
      .setName("test").addScript(LAUNCH_SCRIPT).build();
  }

  @Bean
  public HibernateTransactionManager transactionManager() {
    HibernateTransactionManager man = new HibernateTransactionManager();
    man.setSessionFactory(sessionFactory().getObject());
    return man;
  }
}

package com.xadmin.springboothibernate.config;

import java.util.Properties;

import javax.activation.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

@PropertySource(value= {"classpath:application.properties"})
@Configuration
public class DBConfig {
	
	@Value("${jdbc.driverClassName}")
	private String driverClass;
	@Value("${jdbc.url}")
	private String url;
	@Value("${jdbc.username}")
	private String username;
	@Value("${jdbc.password}")
	private String password;
	@Value("${hibernate.dialect}")
	private String dialect;
	
	@Bean
	public javax.sql.DataSource getdataSource() {
		DriverManagerDataSource datasource = new DriverManagerDataSource(url, username, password);
		datasource.setDriverClassName(driverClass);
		return  datasource;
	}
	@Bean
	public Properties hibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", dialect);
		properties.put("hibernate.hbm2ddl.auto", "update");
		properties.put("hibernate.show.sql", "true");
		properties.put("hibernate.format_sql", "true");
		return properties;
		
		
	}
	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean factory = new LocalSessionFactoryBean();
		factory.setDataSource(getdataSource());
		factory.setHibernateProperties(hibernateProperties());
		factory.setPackagesToScan(new String[] {"com.xadmin.springboot.model"});
		return factory;
	
	}
	
	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory factory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(factory);
		return transactionManager;
		
	}
		
	}



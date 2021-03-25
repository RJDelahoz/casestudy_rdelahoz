package com.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

import static org.hibernate.cfg.AvailableSettings.*;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("com.app.repo")
@PropertySource("classpath:db.properties")
public class DataConfig {

	@Autowired
	private Environment env;

	@Bean(name = "entityManagerFactory")
	LocalContainerEntityManagerFactoryBean getEntityManagerFactoryBean() {
		LocalContainerEntityManagerFactoryBean emfBean =
				new LocalContainerEntityManagerFactoryBean();

		emfBean.setJpaVendorAdapter(getJpaVendorAdapter());
		emfBean.setDataSource(getDataSource());
		emfBean.setPersistenceUnitName(env.getRequiredProperty("persistence.unit_name"));
		emfBean.setPackagesToScan(env.getRequiredProperty("persistence.entity_package"));
		emfBean.setJpaProperties(getHibernateProperties());

		return emfBean;
	}

	@Bean(name = "transactionManager")
	PlatformTransactionManager txManager() {
		return new JpaTransactionManager(
				Objects.requireNonNull(getEntityManagerFactoryBean().getObject()));
	}

	@Bean
	Properties getHibernateProperties() {
		Properties properties = new Properties();

		properties.put(DIALECT, env.getRequiredProperty("hibernate.dialect"));
		properties.put(SHOW_SQL, env.getRequiredProperty("hibernate.show_sql"));
		properties.put(FORMAT_SQL, env.getRequiredProperty("hibernate.format_sql"));
		properties.put(HBM2DDL_AUTO, env.getRequiredProperty("hibernate.hbm2ddl.auto"));
		properties.put(ENABLE_LAZY_LOAD_NO_TRANS,
				env.getRequiredProperty("hibernate.enable_lazy_load_no_trans"));

		return properties;
	}

	@Bean
	DataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource(
				env.getRequiredProperty("jdbc.url"),
				env.getRequiredProperty("jdbc.username"),
				env.getRequiredProperty("jdbc.password"));
		dataSource.setDriverClassName(env.getRequiredProperty("jdbc.driver"));

		return dataSource;
	}

	@Bean
	JpaVendorAdapter getJpaVendorAdapter() {
		return new HibernateJpaVendorAdapter();
	}

}

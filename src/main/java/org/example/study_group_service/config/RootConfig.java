package org.example.study_group_service.config;

import java.util.Properties;
import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import liquibase.integration.spring.SpringLiquibase;


@ComponentScan(basePackages = {"org.example.study_group_service"})
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "org.example.study_group_service.repository")
public class RootConfig {

    PropertyLoader pl = new PropertyLoader("application.properties");

    @Bean
    public DataSource dataSource() {
        HikariConfig cfg = new HikariConfig();
        cfg.setJdbcUrl(pl.get("db.url"));
        cfg.setUsername(pl.get("db.username"));
        cfg.setPassword(pl.get("db.password"));
        cfg.setDriverClassName(pl.get("db.driver"));
        HikariDataSource ds = new HikariDataSource(cfg);

        return new HikariDataSource(cfg);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource ds) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(ds);
        emf.setPackagesToScan("org.example.study_group_service.models.entity");
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        Properties jpaProps = new Properties();
        jpaProps.put("hibernate.hbm2ddl.auto", pl.getOrDefault("jpa.hibernate.ddl-auto", "validate"));
        jpaProps.put("hibernate.dialect", pl.get("jpa.hibernate.dialect"));
        jpaProps.put("hibernate.format_sql", pl.getOrDefault("jpa.hibernate.format_sql", "false"));
        jpaProps.put("hibernate.show_sql", pl.getOrDefault("jpa.show_sql", "false"));
        emf.setJpaProperties(jpaProps);
        return emf;
    }

    @Bean
    public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean emf) {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(emf.getObject());
        return tm;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    // Liquibase
    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase lb = new SpringLiquibase();
        lb.setDataSource(dataSource);
        lb.setChangeLog(pl.get("liquibase.change-log"));
        lb.setDefaultSchema(pl.get("liquibase.default-schema"));
        lb.setDropFirst(Boolean.parseBoolean(pl.getOrDefault("liquibase.drop-first","false")));
        return lb;
    }
}

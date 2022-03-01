package com.warp.unicorn.adapter.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@ConditionalOnExpression("${application.jdbc:false}")
public class DataSourceConfig {

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String userName;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.driver-class-name}")
    private String className;
    @Value("${spring.jpa.properties.hibernate.default_schema:public}")
    private String schema;
    @Value("${spring.datasource.hikari.minimum-idle:1}")
    private Integer idle;
    @Value("${spring.datasource.hikari.max-lifetime:600000}")
    private Integer lifetime;
    @Value("${spring.datasource.hikari.maximum-pool-size:30}")
    private Integer poolSize;

    @Bean(name = "hikariDataSource")
    public DataSource hikariDataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        HikariDataSource dataSource;
        hikariConfig.setDriverClassName(className);
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(userName);
        hikariConfig.setPassword(password);
        hikariConfig.setMinimumIdle(idle);
        hikariConfig.setMaximumPoolSize(poolSize);
        hikariConfig.setMaxLifetime(lifetime);
        hikariConfig.setSchema(schema);
        dataSource = new HikariDataSource(hikariConfig);
        return dataSource;
    }

}

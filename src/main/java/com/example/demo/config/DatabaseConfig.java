package com.example.demo.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Created by Adwiti on 5/5/2018.
 */
@Configuration
public class DatabaseConfig {

    @Bean(name = "db1DataSource")
    @Qualifier("db1DataSource")
    @ConfigurationProperties(prefix = "spring.d1.datasource")
    public DataSource database1DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "db1JdbcTemplate")
    @Qualifier("db1DataSource")
    public JdbcTemplate db1JdbcTemplate(@Qualifier("db1DataSource") DataSource db1DataSource) {
        return new JdbcTemplate(db1DataSource);

    }

    @Bean(name = "db2DataSource")
    @Qualifier("db2DataSource")
    @ConfigurationProperties(prefix = "spring.d2.datasource")
    public DataSource database2DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "db2JdbcTemplate")
    @Qualifier("db2DataSource")
    public JdbcTemplate db2JdbcTemplate(@Qualifier("db2DataSource") DataSource db1DataSource) {
        return new JdbcTemplate(db1DataSource);

    }
}

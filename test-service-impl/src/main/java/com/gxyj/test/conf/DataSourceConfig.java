package com.gxyj.test.conf;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourceConfig {
	
	@Bean(name = "writeDataSource",destroyMethod = "close")
	@ConfigurationProperties(prefix = "spring.datasource.master")
	@Primary
	public DataSource writeDataSource() {
        return DataSourceBuilder.create().build();
    }
	
	
	@Bean(name = "readDataSource",destroyMethod = "close")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource readDataSource() {
        return DataSourceBuilder.create().build();
    }
	
	
	
	
}

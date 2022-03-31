package io.powerledger.localization.datasource;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.zaxxer.hikari.HikariDataSource;

/**
 * 
 * 
 * @author Gustavo Ferreira Machado - <gustavodarrn@gmail.com>
 * @since 30/03/2022
 * @version 1.0
 */
@Configuration
public class LocalizationDataSourceConfiguration {

	@Autowired
	@Qualifier("localizationDataSourceProperties")
	private DataSourceProperties dataSourceProperties;
	
	@Bean("localizationDataSource")
	@Primary
	@ConfigurationProperties("spring.datasource.localization.configuration") 
	public DataSource localizationDataSource() {
		return dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
	}
	
}

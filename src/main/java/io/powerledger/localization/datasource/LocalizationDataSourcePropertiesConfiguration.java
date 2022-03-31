package io.powerledger.localization.datasource;

import java.util.Properties;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 
 * 
 * @author Gustavo Ferreira Machado - <gustavodarrn@gmail.com>
 * @since 30/03/2022
 * @version 1.0
 */
@Configuration
public class LocalizationDataSourcePropertiesConfiguration {

	@Bean("localizationDataSourceProperties")
	@Primary
	@ConfigurationProperties("spring.datasource.localization")
	public DataSourceProperties localizationDataSourceProperties() {
		return new DataSourceProperties();
	}
	
	@Bean("localizationEntityManagerFactoryProperties")
	@ConfigurationProperties("spring.datasource.localization.emfactory")
	public Properties localizationEntityManagerFactoryProperties() {
		return new Properties();
	}
}

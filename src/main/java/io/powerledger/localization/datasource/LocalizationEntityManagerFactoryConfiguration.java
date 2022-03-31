package io.powerledger.localization.datasource;

import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 
 * 
 * @author Gustavo Ferreira Machado - <gustavodarrn@gmail.com>
 * @since 30/03/2022
 * @version 1.0
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
	basePackages = "io.powerledger.localization.dao",
	entityManagerFactoryRef = "localizationEntityManagerFactory",
	transactionManagerRef = "localizationTransactionManager"
)
public class LocalizationEntityManagerFactoryConfiguration {

	@Autowired
	@Qualifier("localizationDataSource")
	private DataSource dataSource;
	
	@Autowired
	@Qualifier("localizationEntityManagerFactoryProperties")
	private Properties emFactoryProperties;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Primary
	@Bean(name = "localizationEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean localizationEntityManagerFactory(EntityManagerFactoryBuilder builder) {
		return builder
                .dataSource(dataSource)
                .properties((Map<String, Object>) (Map) emFactoryProperties)
                .packages("io.powerledger.localization.model")
                .build();
	}
	
	@Primary
	@Bean
	public PlatformTransactionManager localizationTransactionManager(final @Qualifier("localizationEntityManagerFactory") LocalContainerEntityManagerFactoryBean localizationEntityManagerFactory) {
		return new JpaTransactionManager(localizationEntityManagerFactory.getObject());
	}
}

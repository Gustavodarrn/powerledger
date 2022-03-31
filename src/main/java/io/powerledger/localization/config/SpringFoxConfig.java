package io.powerledger.localization.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * 
 * @author Gustavo Ferreira Machado - <gustavodarrn@gmail.com>
 * @since 30/03/2022
 * @version 1.0
 */
@Configuration
@EnableSwagger2
public class SpringFoxConfig {
	
	@Bean
	public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))              
          .paths(PathSelectors.any())                          
          .build()
          .apiInfo(metaData());                                           
    }
	
	private ApiInfo metaData() {
	    return new ApiInfoBuilder()
	        .title("Localization API")
	        .description("\"API to provide and maintain localization data.\"")
	        .version("1.0.0")
	        .build();
	}
}

package io.powerledger.localization.to;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 
 * 
 * @author Gustavo Ferreira Machado - <gustavodarrn@gmail.com>
 * @since 30/03/2022
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionTO {
	
	@JsonInclude(Include.NON_NULL)
    private String code;
    
    private String message;
    
    @JsonInclude(Include.NON_NULL)
    private String stackTrace;

}

package io.powerledger.localization.to;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 * 
 * @author Gustavo Ferreira Machado - <gustavodarrn@gmail.com>
 * @since 30/03/2022
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class ErrorTO {
	private HttpStatus code;    
    private List<String> messages;

    public ErrorTO(HttpStatus status, String error) {
        super();
        this.code = status;
        messages = Arrays.asList(error);
    }
}

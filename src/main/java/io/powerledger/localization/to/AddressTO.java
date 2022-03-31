package io.powerledger.localization.to;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 
 * 
 * @author Gustavo Ferreira Machado - <gustavodarrn@gmail.com>
 * @since 30/03/2022
 * @version 1.0
 */
@Valid
@Data
public class AddressTO {
	
	@NotBlank(message = "\"SUBURBNAME\" field not informed")
	private String suburbName;
	
	@NotNull(message = "\"postcode\" field not informed")	
	//@Pattern(regexp = "^[0-9]*$", message = "\"POSTCODE\": field '${validatedValue}' must contain only numbers.")
	private Integer postcode;
}

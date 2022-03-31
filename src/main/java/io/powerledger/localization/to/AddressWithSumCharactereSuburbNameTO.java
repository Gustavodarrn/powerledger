package io.powerledger.localization.to;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;


/**
 * 
 * 
 * @author Gustavo Ferreira Machado - <gustavodarrn@gmail.com>
 * @since 30/03/2022
 * @version 1.0
 */
@Data
public class AddressWithSumCharactereSuburbNameTO {
	
	private List<AddressTO> listAddress;  
	
	private Integer totalCharacterNumbers;
}

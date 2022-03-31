package io.powerledger.localization.service;

import java.util.List;
import java.util.Optional;

import io.powerledger.localization.exception.ParameterException;
import io.powerledger.localization.to.AddressTO;
import io.powerledger.localization.to.AddressWithSumCharactereSuburbNameTO;

/**
 * 
 * 
 * @author Gustavo Ferreira Machado - <gustavodarrn@gmail.com>
 * @since 30/03/2022
 * @version 1.0
 */
public interface AddressService {
	public List<AddressTO> saveAllAddress(List<AddressTO> listAddressTO);
	
	public AddressWithSumCharactereSuburbNameTO findSuburbNamesByRangePostCodeAndSumCharactereSuburbName(Optional<Integer> beginPostCode, Optional<Integer> endPostCode) throws ParameterException;
}

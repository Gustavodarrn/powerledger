package io.powerledger.localization.facade.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.powerledger.localization.exception.ParameterException;
import io.powerledger.localization.facade.AddressFacade;
import io.powerledger.localization.service.AddressService;
import io.powerledger.localization.to.AddressTO;
import io.powerledger.localization.to.AddressWithSumCharactereSuburbNameTO;

/**
 * 
 * 
 * @author Gustavo Ferreira Machado - <gustavodarrn@gmail.com>
 * @since 30/03/2022
 * @version 1.0
 */
@Service
public class AddressFacadeImpl implements AddressFacade{
	
	@Autowired
	AddressService addressService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<AddressTO> saveAllAddress(List<AddressTO> listAddressTO) {
		return addressService.saveAllAddress(listAddressTO);
	}

	@Override
	public AddressWithSumCharactereSuburbNameTO findSuburbNamesByRangePostCodeAndSumCharactereSuburbName(
			Optional<Integer> beginPostCode, Optional<Integer> endPostCode) throws ParameterException{
		
		return addressService.findSuburbNamesByRangePostCodeAndSumCharactereSuburbName(beginPostCode, endPostCode);
	}
	
}

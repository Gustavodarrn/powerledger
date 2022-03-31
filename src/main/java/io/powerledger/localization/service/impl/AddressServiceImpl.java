package io.powerledger.localization.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.powerledger.localization.dao.AddressDao;
import io.powerledger.localization.exception.ParameterException;
import io.powerledger.localization.mapper.AddressMapper;
import io.powerledger.localization.model.Address;
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
public class AddressServiceImpl implements AddressService {
	
	@Autowired
	private AddressDao addressDao; 
	
	@Override
	public List<AddressTO> saveAllAddress(List<AddressTO> listAddressTO) {
		
		List<Address> listAddress = addressDao.saveAll(AddressMapper.mapFromDTO(listAddressTO));
		return AddressMapper.mapToDTO(listAddress);
	}

	@Override
	public AddressWithSumCharactereSuburbNameTO findSuburbNamesByRangePostCodeAndSumCharactereSuburbName(Optional<Integer> beginPostCode, Optional<Integer> endPostCode) throws ParameterException{
		AddressWithSumCharactereSuburbNameTO addressWithSumCharactereSuburbNameTO = new AddressWithSumCharactereSuburbNameTO();
		
		if(beginPostCode.isEmpty() || endPostCode.isEmpty()) {
			throw new ParameterException();
		}
		
		List<Address> listAddress = addressDao.findByAddressIdPostcodeBetweenOrderByAddressIdSuburbNameAsc(beginPostCode.get(), endPostCode.get());
		
		addressWithSumCharactereSuburbNameTO.setListAddress(AddressMapper.mapToDTO(listAddress)); 
		addressWithSumCharactereSuburbNameTO.setTotalCharacterNumbers(sumCharactereSuburbName(listAddress));  
		
		return addressWithSumCharactereSuburbNameTO;
	}
	
	private int sumCharactereSuburbName(List<Address> listAddress) {
		return listAddress.stream().map(a -> a.getAddressId().getSuburbName().length()).collect(Collectors.summingInt(Integer::intValue));
	}
	
}

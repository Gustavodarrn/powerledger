package io.powerledger.localization;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.powerledger.localization.dao.AddressDao;
import io.powerledger.localization.exception.ParameterException;
import io.powerledger.localization.model.Address;
import io.powerledger.localization.model.AddressId;
import io.powerledger.localization.service.impl.AddressServiceImpl;
import io.powerledger.localization.to.AddressWithSumCharactereSuburbNameTO;

/**
 * 
 * 
 * @author Gustavo Ferreira Machado - <gustavodarrn@gmail.com>
 * @since 30/03/2022
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
public class AddressServiceTests {
	
	@InjectMocks
	AddressServiceImpl addressService;
	
	@Mock
	AddressDao addressDao;
	
	List<Address> listAddress;
	
	Address address1;
	Address address2;
	
	@BeforeEach
	public void setUp() {
		address1 = new Address();		
		address1.setAddressId(new AddressId("BLACKTOWN NSW", 2148));
		
		address2 = new Address();		
		address2.setAddressId(new AddressId("PARRAMATTA NSW", 2150));
		
		listAddress = Arrays.asList(address1, address2);
	}
	
	@Test
	@DisplayName("Test to check the calculation of the sum of the length of the combined names.")
    public void sumCharactereSuburbNameTest() throws ParameterException {
		
	    when(addressDao.findByAddressIdPostcodeBetweenOrderByAddressIdSuburbNameAsc(1000, 3000)).thenReturn(listAddress);
	    
	    AddressWithSumCharactereSuburbNameTO addressWithSumCharactereSuburbNameTO = addressService.findSuburbNamesByRangePostCodeAndSumCharactereSuburbName(Optional.of(1000), Optional.of(3000));
	    
	    assertEquals(address1.getAddressId().getSuburbName().length() + address2.getAddressId().getSuburbName().length(),
	    		addressWithSumCharactereSuburbNameTO.getTotalCharacterNumbers());
		
	    verify(addressDao, times(1)).findByAddressIdPostcodeBetweenOrderByAddressIdSuburbNameAsc(1000, 3000); 
	}
	
	@Test 
 	@DisplayName("Test to verify that the input parameters are valid.")
    public void findSuburbNamesByRangePostCodeInvalidParameter() {
		
		Exception exception = assertThrows(ParameterException.class, () -> {
			addressService.findSuburbNamesByRangePostCodeAndSumCharactereSuburbName(Optional.empty(), Optional.empty());
	    });
		
		String expectedMessage = "Invalid parameters.";
	    String actualMessage = exception.getMessage();
	    
	    assertTrue(actualMessage.contains(expectedMessage));
	}
	
}

package io.powerledger.localization;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.powerledger.localization.controller.AddressController;
import io.powerledger.localization.facade.AddressFacade;
import io.powerledger.localization.to.AddressTO;
import io.powerledger.localization.to.AddressWithSumCharactereSuburbNameTO;

/**
 * 
 * 
 * @author Gustavo Ferreira Machado - <gustavodarrn@gmail.com>
 * @since 30/03/2022
 * @version 1.0
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(AddressController.class)
class AddressStandaloneControllerTests {

	@Autowired
	AddressController addressController;
	
	@MockBean
	AddressFacade addressFacade;
	
	@Autowired
	MockMvc mockController;
	
	AddressWithSumCharactereSuburbNameTO addressWithSumCharactereSuburbNameTO;
	List<AddressTO> listAddressTO;
	
	@BeforeEach
	public void setUp() {
		AddressTO addressTO = new AddressTO();		
		addressTO.setSuburbName("BLACKTOWN NSW");
	    addressTO.setPostcode(2148);
		
		listAddressTO = Arrays.asList(addressTO);
	    addressWithSumCharactereSuburbNameTO = new AddressWithSumCharactereSuburbNameTO();
	    addressWithSumCharactereSuburbNameTO.setListAddress(listAddressTO); 
	    addressWithSumCharactereSuburbNameTO.setTotalCharacterNumbers(13);	    
	}
	
	@Test
	@DisplayName("Test to verify that the API returns the response when the data is passed correctly for the correct URL and parameters. URL: /address/postcode/range")
	void findSuburbNamesByRangePostCodeAndSumCharactereSuburbNameSuccessTest() throws Exception{
		
	    Mockito.when(addressFacade.findSuburbNamesByRangePostCodeAndSumCharactereSuburbName(Optional.of(1), Optional.of(100)))
	    .thenReturn(addressWithSumCharactereSuburbNameTO);
		
	    mockController.perform(get("/address/postcode/range?beginPostCode=1000&endPostCode=3000"))
	                  .andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("Test to verify that data is being saved relative to suburbNames and postecode. URL: /address")
	void saveAllAddressSuccessTest() throws Exception{
		
	    Mockito.when(addressFacade.saveAllAddress(listAddressTO)).thenReturn(listAddressTO);
		
	    mockController.perform(post("/address/")
	    		      .content(asJsonString(listAddressTO))
	    		      .contentType(MediaType.APPLICATION_JSON)
	    		      .accept(MediaType.APPLICATION_JSON))
	    	      	  .andExpect(status().isCreated())
	    	      	  .andExpect(MockMvcResultMatchers.jsonPath("$.[0].postcode").exists())
	    	      	  .andExpect(MockMvcResultMatchers.jsonPath("$.[0].suburbName").exists())
	    	      	  .andExpect(status().is2xxSuccessful());
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	      return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	      throw new RuntimeException(e);
	    }
	  }

}

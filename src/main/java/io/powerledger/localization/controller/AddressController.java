package io.powerledger.localization.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.powerledger.localization.exception.NotFoundException;
import io.powerledger.localization.exception.ParameterException;
import io.powerledger.localization.facade.AddressFacade;
import io.powerledger.localization.to.AddressTO;
import io.powerledger.localization.to.AddressWithSumCharactereSuburbNameTO;
import io.powerledger.localization.to.ErrorTO;
import io.powerledger.localization.to.ExceptionTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 
 * 
 * @author Gustavo Ferreira Machado - <gustavodarrn@gmail.com>
 * @since 30/03/2022
 * @version 1.0
 */
@RestController
@RequestMapping("/address")
@Validated
public class AddressController {
	
	private static final Logger LOG = LoggerFactory.getLogger(AddressController.class);
	
	@Autowired
	private AddressFacade addressFacade;
	
	@GetMapping(value="postcode/range", produces = {MediaType.APPLICATION_JSON_VALUE} )
	@ApiResponses({
		@ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "Result retrieved successfully."),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "Invalid parameters.", response = ErrorTO.class, responseContainer = "List"),		
	})
	@ApiOperation(value = "Returns suburb names in alphabetical order in the given range and calculates the number of characters of all the names combined.")	
    public AddressWithSumCharactereSuburbNameTO findSuburbNamesByRangePostCode(
    		@ApiParam @RequestParam(name = "beginPostCode", required = false)  Optional<Integer> beginPostCode,		
			@ApiParam @RequestParam(name = "endPostCode", required = false)  Optional<Integer> endPostCode,
			HttpServletRequest request) throws NotFoundException, ParameterException {
		
		LOG.debug("findSuburbNamesByRangePostCode begin: beginPostCode: " + beginPostCode + " endPostCode: " + endPostCode);
		
		AddressWithSumCharactereSuburbNameTO addressWithSumCharactereSuburbNameTO = addressFacade.findSuburbNamesByRangePostCodeAndSumCharactereSuburbName(beginPostCode, endPostCode);
		LOG.debug("findSuburbNamesByRangePostCode final: addressWithSumCharactereSuburbNameTO: " + addressWithSumCharactereSuburbNameTO);
		
		return addressWithSumCharactereSuburbNameTO;
    }
	
	@ApiResponses({
		@ApiResponse(code = org.apache.http.HttpStatus.SC_CREATED, message = "Suburbs Names and Postcodes were saved successfully."),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "There is one or more invalid PostCodes and SuburbNames.", response = ExceptionTO.class),
	})
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/")
	@ApiOperation(value = "Save list Postcodes and Suburb Names")
	public List<AddressTO> saveAllAddress(@RequestBody @Valid List<AddressTO> listAddress, final HttpServletRequest request){
		LOG.debug("saveAllAddress begin: listAddress " + listAddress); 
		List<AddressTO> listAddressReturn =  addressFacade.saveAllAddress(listAddress);		
		
		LOG.debug("saveAllAddress final: listAddress " + listAddressReturn);		
		return listAddressReturn;
    }

}

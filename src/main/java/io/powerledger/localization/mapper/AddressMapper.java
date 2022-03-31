package io.powerledger.localization.mapper;

import java.util.ArrayList;
import java.util.List;

import io.powerledger.localization.model.Address;
import io.powerledger.localization.model.AddressId;
import io.powerledger.localization.to.AddressTO;

/**
 * 
 * 
 * @author Gustavo Ferreira Machado - <gustavodarrn@gmail.com>
 * @since 30/03/2022
 * @version 1.0
 */
public class AddressMapper {

	public static AddressTO mapToDTO(Address address) {
		AddressTO to = new AddressTO();

		to.setSuburbName(address.getAddressId().getSuburbName());
		to.setPostcode(address.getAddressId().getPostcode());

		return to;
	}
	
	public static List<AddressTO> mapToDTO(List<Address> listAddress) {
		List<AddressTO> listAddressTO = new ArrayList<>();

		for (Address address : listAddress) {
			listAddressTO.add(mapToDTO(address));
		}

		return listAddressTO;
	}
	
	public static Address mapFromDTO(AddressTO addressTO) {
		Address address = new Address();
		address.setAddressId(new AddressId(addressTO.getSuburbName(), addressTO.getPostcode()));
		return address;
	}
	
	public static List<Address> mapFromDTO(List<AddressTO> listAddressTO) {
		List<Address> listAddress = new ArrayList<>();

		for (AddressTO addressTO : listAddressTO) {
			listAddress.add(mapFromDTO(addressTO));
		}
		return listAddress;
	}

}

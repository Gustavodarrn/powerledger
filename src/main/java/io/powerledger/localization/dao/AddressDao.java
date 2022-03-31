package io.powerledger.localization.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.powerledger.localization.model.Address;
import io.powerledger.localization.model.AddressId;

/**
 * 
 * 
 * @author Gustavo Ferreira Machado - <gustavodarrn@gmail.com>
 * @since 30/03/2022
 * @version 1.0
 */
@Repository
public interface AddressDao extends JpaRepository<Address, AddressId> {
	
	//Solution with Query Param
	//@Query(value = "select * from address where address.postcode between :beginPostCode and :endPostCode order by address.suburb_name asc", nativeQuery = true)
	//List<Address> findByAddressIdPostcodeBetweenOrderByAddressIdSuburbNameAsc( @Param("beginPostCode")Integer beginPostCode, @Param("endPostCode") Integer endPostCode);
	
	List<Address> findByAddressIdPostcodeBetweenOrderByAddressIdSuburbNameAsc(Integer beginPostCode, Integer endPostCode);
	
}

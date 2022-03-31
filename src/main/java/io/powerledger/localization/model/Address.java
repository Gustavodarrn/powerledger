package io.powerledger.localization.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * 
 * @author Gustavo Ferreira Machado
 * @since 28/03/2022
 * @version 1.0
 */
@Entity
@Table(name = "address")
@Data
@EqualsAndHashCode
public class Address {
	
	@EmbeddedId
    private AddressId addressId;
	
}

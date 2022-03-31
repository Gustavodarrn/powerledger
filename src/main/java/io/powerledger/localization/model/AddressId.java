package io.powerledger.localization.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 * 
 * @author Gustavo Ferreira Machado
 * @since 28/03/2022
 * @version 1.0
 */
@Embeddable
@Data
@AllArgsConstructor
public class AddressId implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public AddressId() {
		super();
	}
	
	@Column(name = "suburb_name")
	private String suburbName;
	
	
	@Column(name = "postcode")
	private Integer postcode;
	
}

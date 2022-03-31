package io.powerledger.localization.exception;

/**
 * 
 * 
 * @author Gustavo Ferreira Machado - <gustavodarrn@gmail.com>
 * @since 28/03/2022
 * @version 1.0
 */
public class ParameterException extends Exception {

	private static final long serialVersionUID = 1L;

	public ParameterException() {
		super("Invalid parameters.");
	}
}

/**
 * 
 */
package io.powerledger.localization;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import io.powerledger.localization.exception.NotFoundException;
import io.powerledger.localization.exception.ParameterException;
import io.powerledger.localization.to.ErrorTO;
import io.powerledger.localization.to.ExceptionTO;


/**
 * 
 * 
 * @author Gustavo Ferreira Machado - <gustavodarrn@gmail.com>
 * @since 30/03/2022
 * @version 1.0
 */
@ControllerAdvice
public class LocalizationExceptionHandler {

	private static final Logger LOG = LoggerFactory.getLogger(LocalizationExceptionHandler.class);

	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> handleConstraintViolation(
	  ConstraintViolationException ex, WebRequest request) {
	    List<String> errors = new ArrayList<String>();
	    for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
	        errors.add( violation.getMessage());
	    }
	    ErrorTO errorTO = new ErrorTO(HttpStatus.BAD_REQUEST, errors);
	    return new ResponseEntity<Object>(errorTO, new HttpHeaders(), errorTO.getCode());
	}

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<ExceptionTO> exception(final MethodArgumentNotValidException exception) {
		LOG.warn(exception.getMessage());
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		final List<String> errors = new ArrayList<>();
		for (FieldError fieldError : fieldErrors) {
			String mensagem = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			errors.add(mensagem);
		}
		List<ObjectError> globalErrors = exception.getBindingResult().getGlobalErrors();
		for (ObjectError objectError : globalErrors) {
			String mensagem = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
			errors.add(mensagem);
		}
		return getResponseEntity(HttpStatus.BAD_REQUEST, StringUtils.join(errors, ", "));
	}
	
	
	@ExceptionHandler(value = BindException.class)
	public ResponseEntity<ExceptionTO> exception(final BindException exception) {
		LOG.warn(exception.getMessage());
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		final List<String> erros = new ArrayList<>();
		for (FieldError fieldError : fieldErrors) {
			String mensagem = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			erros.add(mensagem);
		}
		List<ObjectError> globalErrors = exception.getBindingResult().getGlobalErrors();
		for (ObjectError objectError : globalErrors) {
			String mensagem = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
			erros.add(mensagem);
		}
		return getResponseEntity(HttpStatus.BAD_REQUEST, StringUtils.join(erros, ", "));
	}
	
	private static String getErrorCode() {
		final String hexTimestamp = Long.toHexString(System.currentTimeMillis()).toUpperCase();
		String randomHex = Integer.toHexString(ThreadLocalRandom.current().nextInt(1000, 10000)).toUpperCase();
		return hexTimestamp+"@"+randomHex;
	}

	private static ResponseEntity<ExceptionTO> getResponseEntity(final HttpStatus code, final String message) {
		ExceptionTO exceptionTO = new ExceptionTO();
		exceptionTO.setCode(String.valueOf(code.value()));
		exceptionTO.setMessage(message);
		return new ResponseEntity<>(exceptionTO, code);
	}
	
	

	private ExceptionTO getError500(final Exception exception, String errorCode) {
		ExceptionTO exceptionTO = new ExceptionTO();
		exceptionTO.setCode(errorCode);
		exceptionTO.setMessage("An unexpected error has occurred. (error code: " + errorCode + ")");		
		exceptionTO.setStackTrace(ExceptionUtils.getStackTrace(exception));		
		return exceptionTO;
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ExceptionTO> exception(final Exception exception) {
		final String errorCode = getErrorCode();
		LOG.error("Ticket " + errorCode, exception);
		ExceptionTO exceptionTO = this.getError500(exception, errorCode);

		return new ResponseEntity<>(exceptionTO, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = NotFoundException.class)
	public ResponseEntity<ExceptionTO> exception(final NotFoundException exception) {
		return getResponseEntity(HttpStatus.NOT_FOUND, exception.getMessage());
	}
	
	
	@ExceptionHandler(value = ParameterException.class)
	public ResponseEntity<ExceptionTO> exception(final ParameterException exception) {
		return getResponseEntity(HttpStatus.BAD_REQUEST, exception.getMessage());
	}
	
}

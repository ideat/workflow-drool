package com.mindware.workflow.exception;

import com.mindware.workflow.core.exception.UseCaseException;
import com.mindware.workflow.core.exception.ValidacionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException e,
                                                                          HttpHeaders headers, HttpStatus status, WebRequest request) {
		logException(HttpStatus.BAD_REQUEST, e);

		String error = e.getParameterName() + " parameter is missing.";
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error));
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException e,
                                                                     HttpHeaders headers, HttpStatus status, WebRequest request) {
		logException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, e);

		StringBuilder builder = new StringBuilder();
		builder.append(e.getContentType());
		builder.append(" media type is not supported. Supported media types are: ");
		e.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
		return buildResponseEntity(
				new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, builder.substring(0, builder.length() - 2)));
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
		logException(HttpStatus.BAD_REQUEST, e);

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
		apiError.setMessage(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
		return buildResponseEntity(apiError);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
		logException(HttpStatus.BAD_REQUEST, e);

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
		apiError.setMessage("Malformed JSON creditRequest.");
		return buildResponseEntity(apiError);
	}
	
	//Manejadores personsalizados
	@ExceptionHandler(ValidacionException.class)
	public ResponseEntity<Object> handleValidacionException(ValidacionException e) {
		logException(HttpStatus.BAD_REQUEST, e);

		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, e.getMessage()));
	}

	@ExceptionHandler(UseCaseException.class)
	public ResponseEntity<Object> handleErpException(UseCaseException e) {
		logException(HttpStatus.UNPROCESSABLE_ENTITY, e);

		return buildResponseEntity(new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage()));
	}
	
	@ExceptionHandler(IndexOutOfBoundsException.class)
	public ResponseEntity<Object> handleErpException(IndexOutOfBoundsException e) {
		logException(HttpStatus.NOT_FOUND, e);

		return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, e.getMessage()));
	}	

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception e, WebRequest request) {
		logException(HttpStatus.INTERNAL_SERVER_ERROR, e);

		return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
	}
	
//	@ExceptionHandler(AuthenticationException.class)
//	public ResponseEntity<Object> handleAuthenticationException(AuthenticationException e) {
//		logException(HttpStatus.UNAUTHORIZED, e);
//
//		return buildResponseEntity(new ApiError(HttpStatus.UNAUTHORIZED, e.getMessage()));
//	}

//	@ExceptionHandler({ org.springframework.security.core.AuthenticationException.class })
//	public ResponseEntity<Object> handleAuthenticationException(
//			org.springframework.security.core.AuthenticationException e) {
//		logException(HttpStatus.UNAUTHORIZED, e);
//
//		return buildResponseEntity(new ApiError(HttpStatus.UNAUTHORIZED, e.getMessage()));
//	}

//	@ExceptionHandler({ AuthorizationException.class, AccessDeniedException.class })
//	public ResponseEntity<Object> handleAuthorizationException(Exception e) {
//		logException(HttpStatus.FORBIDDEN, e);
//
//		return buildResponseEntity(new ApiError(HttpStatus.FORBIDDEN, e.getMessage()));
//	}

	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}

	private void logException(HttpStatus status, Throwable e) {
		LOGGER.info("Error with status: {}, message: {}", status, e.getMessage());
		LOGGER.debug("Error with status: {}", status, e);
	}

}

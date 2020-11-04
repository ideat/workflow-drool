package com.mindware.workflow.core.exception;

public class ValidacionException extends UseCaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3294532275146727468L;

	public ValidacionException(String message) {
		super(message);
	}

	public ValidacionException(Exception e) {
		super(e.getMessage(), e);
	}

	public ValidacionException(String message, Exception e) {
		super(message, e);
	}
}

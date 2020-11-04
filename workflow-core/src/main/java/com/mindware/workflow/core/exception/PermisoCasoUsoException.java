package com.mindware.workflow.core.exception;

public class PermisoCasoUsoException extends UseCaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3294532275146727468L;

	public PermisoCasoUsoException(String message) {
		super(message);
	}

	public PermisoCasoUsoException(Exception e) {
		super(e.getMessage(), e);
	}

	public PermisoCasoUsoException(String message, Exception e) {
		super(message, e);
	}
}

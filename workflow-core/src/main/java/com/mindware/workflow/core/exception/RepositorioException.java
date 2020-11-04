package com.mindware.workflow.core.exception;

public class RepositorioException extends UseCaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4817271422252159013L;

	public RepositorioException(String message) {
		super(message);
	}

	public RepositorioException(Exception e) {
		super(e.getMessage(), e);
	}

	public RepositorioException(String message, Exception e) {
		super(message, e);
	}
}

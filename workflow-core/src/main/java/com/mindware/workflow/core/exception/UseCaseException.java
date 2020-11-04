package com.mindware.workflow.core.exception;

public class UseCaseException extends RuntimeException{

    private static final long serialVersionUID = -369764083952373334L;

    public UseCaseException(String message){
        super(message);
    }

    public UseCaseException(Exception e) {
        super(e.getMessage(),e);
    }

    public UseCaseException(String message, Exception e){
        super(message,e);
    }
}

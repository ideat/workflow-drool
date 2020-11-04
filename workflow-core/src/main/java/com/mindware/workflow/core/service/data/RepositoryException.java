package com.mindware.workflow.core.service.data;

public class RepositoryException extends RuntimeException {

    private static final long serialVersionUID = -4817271422252159013L;

    public RepositoryException(String message){
        super(message);
    }

    public RepositoryException(Exception e) {
        super(e.getMessage(),e);
    }

    public RepositoryException(String message, Exception e){
        super(message,e);
    }
}

package com.mindware.workflow.core.usecase;

public interface UseCaseFactory {
    UseCase create(String useCase, Object input);
}

package com.mindware.workflow.core.usecase;

import java.util.Optional;

public interface UseCase<R> {
    void execute();

    Optional<R> getResult();
}

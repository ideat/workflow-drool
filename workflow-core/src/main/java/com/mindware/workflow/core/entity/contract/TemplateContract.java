package com.mindware.workflow.core.entity.contract;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class TemplateContract {
    private UUID id;

    private String fileName;

    private String pathContract;

    private String detail;

    private String active;

}

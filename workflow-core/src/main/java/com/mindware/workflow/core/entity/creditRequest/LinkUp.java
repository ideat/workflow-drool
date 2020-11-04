package com.mindware.workflow.core.entity.creditRequest;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
/*
Binding economic, enterprise, reference persons, guarantor loan
 */
@Getter
@Setter
public class LinkUp {
    private UUID id;

    private String firstField;

    private String seconfField;

    private String thirdField;

    private String fouthField;

    private String fifthField;

    private String typeLinkUp;

}

package com.mindware.workflow.core.entity.config;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
public class Office {

    private UUID id;

    private UUID idRoot;

    private int internalCode;

    @NotNull(message = "'Nombre oficina' no puede ser omitido")
    private String name;

    @NotNull(message = "'Direccion oficina' no puede ser omitida")
    private String address;

    @NotNull(message = "'Telefono oficina' no puede ser omitido")
    private String phone;

    @NotNull(message = "'Ciudad oficina' no puede ser omitida")
    private String city;

    @NotNull(message = "'Provincia oficina' no puede ser omitida")
    private String province;

    @NotNull(message = "'Tipo de oficina' no puede ser omitido")
    private String typeOffice;

    @NotNull(message = "'Responsable de la oficina' no puede ser omitido")
    private String signatorie;

}

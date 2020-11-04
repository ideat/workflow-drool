package com.mindware.workflow.core.entity.rol;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
public class Rol {
    private UUID id;

    @NotNull(message = "'Nombre Rol' no puede ser omitido")
    @NotBlank(message = "'Nombre Rol' no puede estar en blanco")
    private String name;

    private String description;

    @NotNull(message = "'Opciones' no puede ser omitido")
    private String options;

    @NotNull(message = "'Estado' no puede ser omitido")
    private String states;

    @NotNull(message = "Alcance no puede ser omitido")
    private String scope;
}

package com.example.escuela.dto.alumnos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AlumnoRequest(

        @NotBlank(message = "El nombre es requerido")
        @Size(min = 5, max = 50, message = "El nombre debe tener entre 5 y 50 caracteres")
        String nombre,

        @NotBlank(message = "El apellido paterno es requerido")
        @Size(min = 5, max = 50, message = "El apellido debe tener entre 5 y 50 caracteres")
        String apellidoPaterno,

        @NotBlank(message = "El apellido materno es requerido")
        @Size(min = 5, max = 50, message = "El apellido debe tener entre 5 y 50 caracteres")
        String apellidoMaterno
) {}
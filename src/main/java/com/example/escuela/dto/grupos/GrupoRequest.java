package com.example.escuela.dto.grupos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record GrupoRequest(

        @NotNull(message = "El id del curso es requerido")
        @Positive(message = "El id del curso debe ser positivo")
        Long idCurso,

        @NotNull(message = "El id del maestro es requerido")
        @Positive(message = "El id del maestro debe ser positivo")
        Long idMaestro,

        @NotNull(message = "El id del aula es requerida")
        @Positive(message = "El id del aula debe ser positivo")
        Long idAula,

        @NotBlank(message = "El periodo es requerido")
        String periodo
) {}
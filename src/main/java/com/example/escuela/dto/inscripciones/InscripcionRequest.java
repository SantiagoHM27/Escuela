package com.example.escuela.dto.inscripciones;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record InscripcionRequest(

        @NotNull(message = "El id del Alumno es requerido")
        @Positive(message = "El id debe ser positivo")
        Long idAlumno,

        @NotNull(message = "El id del Grupo es requerido")
        @Positive(message = "El id debe ser positivo")
        Long idGrupo


) {}

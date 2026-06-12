package com.example.escuela.dto.calificaciones;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CalificacionRequest(

        @NotNull(message = "El id de la Inscricipcion es requerido")
        @Positive(message = "El id debe ser positivo")
        Long idInscripcion,

        @NotNull(message = "La calificacion es requerida")
        @DecimalMin(value = "0.0", message = "La calificación mínima es 0")
        @DecimalMax(value = "10.0", message = "La calificación máxima es 10")
        BigDecimal calificacion

) {}

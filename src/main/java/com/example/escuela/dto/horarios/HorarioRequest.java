package com.example.escuela.dto.horarios;

import com.example.escuela.enums.DiaSemana;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record HorarioRequest(

        @NotNull(message = "El id del grupo es requerido")
        @Positive(message = "El id debe ser positivo")
        Long idGrupo,

        @NotNull(message = "El dia es requerido")
        String diaSemana,

        @NotBlank(message = "La hora de inicio es requerida")
        String horaInicio,

        @NotBlank(message = "La hora de fin es requerida")
        String horaFin
) {}
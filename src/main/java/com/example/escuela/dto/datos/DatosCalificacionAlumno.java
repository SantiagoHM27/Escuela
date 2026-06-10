package com.example.escuela.dto.datos;

import java.math.BigDecimal;

public record DatosCalificacionAlumno(

        String curso,
        String periodo,
        BigDecimal calificacion
) {
}

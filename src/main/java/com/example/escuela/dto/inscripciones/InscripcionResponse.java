package com.example.escuela.dto.inscripciones;

import com.example.escuela.dto.datos.DatosAlumno;
import com.example.escuela.dto.datos.DatosGrupo;

import java.math.BigDecimal;

public record InscripcionResponse(

        Long id,
        DatosAlumno alumno,
        DatosGrupo grupo,
        String fechaInscripcion,
        BigDecimal calificacion

) {}

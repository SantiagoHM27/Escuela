package com.example.escuela.dto.alumnos;

import com.example.escuela.dto.datos.DatosCalificacionAlumno;
import java.math.BigDecimal;
import java.util.List;

public record AlumnoResponse(
        Long id,
        String nombre,
        String email,
        String matricula,
        String fechaIngreso,
        List<DatosCalificacionAlumno> calificaciones,
        BigDecimal promedio
) {}
package com.example.escuela.dto.alumnos;

import com.example.escuela.dto.datos.DatosCalificacionAlumno;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record AlumnoResponse(
        Long id,
        String nombre,
        String apellidoPaterno,
        String apellidoMaterno,
        String email,
        String matricula,
        LocalDate fechaIngreso,
        List<DatosCalificacionAlumno> calificaciones,
        BigDecimal promedio


){}

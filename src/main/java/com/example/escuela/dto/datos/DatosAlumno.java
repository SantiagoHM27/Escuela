package com.example.escuela.dto.datos;

import java.time.LocalDate;

public record DatosAlumno(
        String nombre,
        String matricula,
        String email,
        LocalDate fechaIngreso

) {}

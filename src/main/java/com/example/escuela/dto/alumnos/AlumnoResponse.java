package com.example.escuela.dto.alumnos;

import java.time.LocalDate;

public record AlumnoResponse(
        Long id,
        String nombre,
        String apellidoPaterno,
        String apellidoMaterno,
        String email,
        String matricula,
        LocalDate fechaIngreso


){}

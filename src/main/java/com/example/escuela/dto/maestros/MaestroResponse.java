package com.example.escuela.dto.maestros;

import com.example.escuela.dto.datos.DatosCurso;

import java.util.List;

public record MaestroResponse(
        Long id,
        String nombre,
        String email,
        String telefono,
        List<DatosCurso> cursos
) {
}

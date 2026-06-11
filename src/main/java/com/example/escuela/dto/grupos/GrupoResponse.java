package com.example.escuela.dto.grupos;

import com.example.escuela.dto.datos.DatosAula;
import com.example.escuela.dto.datos.DatosCurso;
import com.example.escuela.dto.datos.DatosMaestro;

import java.util.List;

public record GrupoResponse(
        Long id,
        DatosCurso curso,
        DatosMaestro maestro,
        DatosAula aula,
        List<String> horarios,
        String periodo

) {}

package com.example.escuela.dto.horarios;

import com.example.escuela.dto.datos.DatosGrupo;

public record HorarioResponse(
        Long id,
        DatosGrupo grupo,
        String horario
) {}
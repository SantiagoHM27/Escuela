package com.example.escuela.enums;

import com.example.escuela.exceptions.RecursoNoEncontradoException;
import com.example.escuela.utils.StringCustomUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DiaSemana {
    LUNES("LUNES"),
    MARTES("MARTES"),
    MIERCOLES("MIERCOLES"),
    JUEVES("JUEVES"),
    VIERNES("VIERNES"),
    SABADO("SABADO");

    private final String descripcion;

    public static DiaSemana obtenerCategoriaPorDescripcion(String descripcion) {
        StringCustomUtils.validarNoVacio(descripcion, "La dexcripcion es requerida");
        String descripcionNormalizada = StringCustomUtils.quitarAcentos(descripcion.trim());
        for (DiaSemana diaSemana : values()) {
            if (StringCustomUtils.quitarAcentos(diaSemana.descripcion).equalsIgnoreCase(descripcionNormalizada))
                return diaSemana;

        }
        throw  new RecursoNoEncontradoException("No existe un dia de la semana  con la descripcion:" + descripcion);
    }
}

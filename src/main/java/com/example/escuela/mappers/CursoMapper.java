package com.example.escuela.mappers;


import com.example.escuela.dto.cursos.CursoRequest;
import com.example.escuela.dto.cursos.CursoResponse;
import com.example.escuela.dto.datos.DatosCurso;
import com.example.escuela.entities.Curso;
import org.springframework.stereotype.Component;

@Component
public class CursoMapper implements CommonMapper<CursoRequest, CursoResponse, Curso> {
    @Override
    public Curso requestAEntidad(CursoRequest request) {
        return null;
    }

    @Override
    public CursoResponse entidadAResponse(Curso entidad) {
        return null;
    }

    public DatosCurso entidadADatosCurso(Curso entidad) {
        if (entidad == null) return  null;

        String descripcion = entidad.getDescripcion() == null ?
                "Sin Descripcion" : entidad.getDescripcion();

        return new DatosCurso(
                entidad.getNombre(),
                descripcion,
                entidad.getCreditos()
        );
    }
}

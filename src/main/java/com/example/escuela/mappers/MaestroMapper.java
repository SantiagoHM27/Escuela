package com.example.escuela.mappers;

import com.example.escuela.dto.datos.DatosCurso;
import com.example.escuela.dto.maestros.MaestroRequest;
import com.example.escuela.dto.maestros.MaestroResponse;
import com.example.escuela.entities.Maestro;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@AllArgsConstructor
public class MaestroMapper implements CommonMapper<MaestroRequest, MaestroResponse, Maestro> {

    private final CursoMapper cursoMapper;

    @Override
    public Maestro requestAEntidad(MaestroRequest request) {
        return Maestro.builder()
                .nombre(request.nombre().trim())
                .apellidoPaterno(request.apellidoPaterno().trim())
                .apellidoMaterno(request.apellidoMaterno().trim())
                .email(request.email().trim())
                .telefono(request.telefono().trim())
                .build();
    }

    @Override
    public MaestroResponse entidadAResponse(Maestro entidad) {
        if (entidad == null) return null;

        List<DatosCurso> cursos = this.entidadADatosCurso(entidad);

        return new MaestroResponse(
                entidad.getId(),
                String.join(" ",
                        entidad.getNombre(),
                        entidad.getApellidoPaterno(),
                        entidad.getApellidoMaterno()
                ),
                entidad.getEmail(),
                entidad.getTelefono(),
                cursos
        );
    }

    private List<DatosCurso> entidadADatosCurso(Maestro entidad) {
        if(entidad == null) return List.of();

        return entidad.getGrupos().stream().map(grupo ->
                cursoMapper.entidadADatosCurso(grupo.getCurso())).toList();
    }
}

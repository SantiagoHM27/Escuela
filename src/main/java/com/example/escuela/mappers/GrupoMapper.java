package com.example.escuela.mappers;

import com.example.escuela.dto.datos.DatosAula;
import com.example.escuela.dto.datos.DatosMaestro;
import com.example.escuela.dto.grupos.GrupoRequest;
import com.example.escuela.dto.grupos.GrupoResponse;
import com.example.escuela.entities.Grupo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class GrupoMapper  implements CommonMapper<GrupoRequest, GrupoResponse, Grupo>{

    private  final CursoMapper cursoMapper;

    @Override
    public Grupo requestAEntidad(GrupoRequest request) {
        return Grupo.builder()
                .periodo(request.periodo().trim())
                .build();
    }

    @Override
    public GrupoResponse entidadAResponse(Grupo entidad) {
        if (entidad == null) return null;

        List<String> horarios = entidad.getHorarios().stream()
                .map(h -> h.getDiaSemana().getDescripcion() + " " + h.getHoraInicio() + " - " + h.getHoraFin())
                .toList();

        return new GrupoResponse(
                entidad.getId(),
                cursoMapper.entidadADatosCurso(entidad.getCurso()),
                new DatosMaestro(
                        String.join(" ",
                                entidad.getMaestro().getNombre(),
                                entidad.getMaestro().getApellidoPaterno(),
                                entidad.getMaestro().getApellidoMaterno()),
                        entidad.getMaestro().getEmail(),
                        entidad.getMaestro().getTelefono()
                ),
                new DatosAula(
                        entidad.getAula().getNombre(),
                        entidad.getAula().getCapacidad()
                ),
                horarios,
                entidad.getPeriodo()
        );
    }

}


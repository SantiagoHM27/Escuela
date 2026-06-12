package com.example.escuela.mappers;

import com.example.escuela.dto.datos.DatosGrupo;
import com.example.escuela.dto.horarios.HorarioRequest;
import com.example.escuela.dto.horarios.HorarioResponse;
import com.example.escuela.entities.Grupo;
import com.example.escuela.entities.Horario;
import com.example.escuela.enums.DiaSemana;
import com.example.escuela.repositories.GrupoRepository;
import com.example.escuela.utils.ServiceUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class HorarioMapper implements CommonMapper<HorarioRequest, HorarioResponse, Horario> {

    private final GrupoRepository grupoRepository;

    @Override
    public Horario requestAEntidad(HorarioRequest request) {
        if (request == null) return null;

        Grupo grupo = ServiceUtils.obtenerEntidadOException(
                grupoRepository, request.idGrupo(), Grupo.class);

        return Horario.builder()
                .grupo(grupo)
                .diaSemana(DiaSemana.obtenerCategoriaPorDescripcion(request.diaSemana()))
                .horaInicio(request.horaInicio())
                .horaFin(request.horaFin())
                .build();
    }

    @Override
    public HorarioResponse entidadAResponse(Horario entidad) {
        if (entidad == null) return null;

        Grupo grupo = entidad.getGrupo();

        String horario = entidad.getDiaSemana().getDescripcion() + " " +
                entidad.getHoraInicio() + " " + entidad.getHoraFin();

        return new HorarioResponse(
                entidad.getId(),
                new DatosGrupo(
                        grupo.getCurso().getNombre(),
                        String.join(" ",
                                grupo.getMaestro().getNombre(),
                                grupo.getMaestro().getApellidoPaterno(),
                                grupo.getMaestro().getApellidoMaterno()),
                        grupo.getAula().getNombre(),
                        grupo.getPeriodo()
                ),
                horario
        );
    }
}

package com.example.escuela.services;

import com.example.escuela.dto.horarios.HorarioRequest;
import com.example.escuela.dto.horarios.HorarioResponse;
import com.example.escuela.entities.Horario;
import com.example.escuela.enums.DiaSemana;
import com.example.escuela.mappers.HorarioMapper;
import com.example.escuela.repositories.GrupoRepository;
import com.example.escuela.repositories.HorarioRepository;
import com.example.escuela.utils.ServiceUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class HorarioServiceImpl implements HorarioService {

    private final HorarioRepository horarioRepository;
    private final GrupoRepository grupoRepository;
    private final HorarioMapper horarioMapper;

    @Override
    @Transactional(readOnly = true)
    public List<HorarioResponse> listar() {
        log.info("Listando todos los horarios");
        return horarioRepository.findAll().stream()
                .map(horarioMapper::entidadAResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public HorarioResponse obtenerPorId(Long id) {
        return horarioMapper.entidadAResponse(obtenerHorario(id));
    }

    @Override
    public HorarioResponse registrar(HorarioRequest request) {
        log.info("Registrando nuevo horario...");

        validarFormato(request.horaInicio(), request.horaFin());

        Horario horario = horarioMapper.requestAEntidad(request);

        validarTraslapes(horario, null);

        horarioRepository.save(horario);
        log.info("Nuevo horario registrado con id: {}", horario.getId());
        return horarioMapper.entidadAResponse(horario);
    }


    @Override
    public HorarioResponse actualizar(HorarioRequest request, Long id) {
        Horario horario = obtenerHorario(id);
        log.info("Actualizando horario con id: {}", id);

        validarFormato(request.horaInicio(), request.horaFin());

        horario.actualizar(
                DiaSemana.obtenerCategoriaPorDescripcion(request.diaSemana()),
                request.horaInicio(),
                request.horaFin()
        );

        validarTraslapes(horario, id);
        log.info("Horario con id {} actualizado", id);
        return horarioMapper.entidadAResponse(horario);
    }

    @Override
    public void eliminar(Long id) {
        Horario horario = obtenerHorario(id);

        log.info("Eliminando horario con id: {}", id);
        horarioRepository.delete(horario);
        log.info("Horario con id {} eliminado", id);
    }

    private Horario obtenerHorario(Long id) {
        return ServiceUtils.obtenerEntidadOException(horarioRepository, id, Horario.class);
    }

    private void validarFormato(String horaInicio, String horaFin) {
        if (!horaInicio.matches("^([01]\\d|2[0-3]):[0-5]\\d$"))
            throw new IllegalArgumentException("Formato inválido en horaInicio, use HH:mm");

        if (!horaFin.matches("^([01]\\d|2[0-3]):[0-5]\\d$"))
            throw new IllegalArgumentException("Formato inválido en horaFin, use HH:mm");

        if (horaFin.compareTo(horaInicio) <= 0)
            throw new IllegalArgumentException("La horaFin debe ser posterior a la horaInicio");
    }

    private void validarTraslapes(Horario horario, Long idExcluir) {
        if (horarioRepository.existsByGrupoIdAndDiaSemanaAndHoraInicioLessThanAndHoraFinGreaterThan(
                horario.getGrupo().getId(),
                horario.getDiaSemana(),
                horario.getHoraFin(),
                horario.getHoraInicio()))
            throw new IllegalArgumentException("Ya existe un horario que se traslapa en ese grupo");

        if (horarioRepository.existsByGrupoAulaIdAndDiaSemanaAndHoraInicioLessThanAndHoraFinGreaterThan(
                horario.getGrupo().getAula().getId(),
                horario.getDiaSemana(),
                horario.getHoraFin(),
                horario.getHoraInicio()))
            throw new IllegalArgumentException("Ya existe un horario que se traslapa en esa aula");
    }

}
package com.example.escuela.services;

import com.example.escuela.dto.calificaciones.CalificacionRequest;
import com.example.escuela.dto.calificaciones.CalificacionResponse;
import com.example.escuela.entities.Calificacion;
import com.example.escuela.entities.Inscripcion;
import com.example.escuela.exceptions.EntidadRelacionadaException;
import com.example.escuela.mappers.CalificacionMapper;
import com.example.escuela.repositories.CalificacionRepository;
import com.example.escuela.repositories.InscripcionRepository;
import com.example.escuela.utils.ServiceUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class CalificacionServiceImpl implements  CalificacionService {

    private final CalificacionRepository calificacionRepository;

    private final InscripcionRepository inscripcionRepository;

    private final CalificacionMapper calificacionMapper;
    @Override
    public List<CalificacionResponse> listar() {

        log.info("Listando las calificaciones solicitadas");
        return calificacionRepository.findAll().stream()
                .map(calificacionMapper::entidadAResponse).toList();

    }

    @Override
    public CalificacionResponse obtenerPorId(Long id) {
        return calificacionMapper.entidadAResponse(obtenerCalificacion(id));
    }

    @Override
    public CalificacionResponse registrar(CalificacionRequest request) {
      log.info("Registrando una nueva calificacion....");

      Inscripcion inscripcion = ServiceUtils.obtenerEntidadOException(
              inscripcionRepository, request.idInscripcion(), Inscripcion.class);

        if (calificacionRepository.existsByInscripcionId(request.idInscripcion()))
            throw new IllegalArgumentException("La inscripción ya tiene una calificación registrada");

        Calificacion calificacion = calificacionMapper.requestAEntidad(request);
        calificacion.setFechaRegistro(LocalDate.now());

        calificacionRepository.save(calificacion);

        log.info("Nueva calificacion registrada con id {}", calificacion.getId());
        return calificacionMapper.entidadAResponse(calificacion);
    }

    @Override
    public CalificacionResponse actualizar(CalificacionRequest request, Long id) {
        Calificacion calificacion = obtenerCalificacion(id);

        log.info("Actualizando calificacion....");

        calificacion.actualizar(
                request.calificacion()
        );

        log.info("Calificacion {} actualizada:", calificacion.getCalificacion());
        return  calificacionMapper.entidadAResponse(calificacion);

    }


    @Override
    public void eliminar(Long id) {
        Calificacion calificacion = obtenerCalificacion(id);

        log.info("Eliminando calificacion con id: {}", id);
        calificacionRepository.delete(calificacion);
        log.info("Calificacion eliminada con id: {}", id);
    }

    private Calificacion obtenerCalificacion(Long id) {
        return ServiceUtils.obtenerEntidadOException(calificacionRepository, id, Calificacion.class);
    }
}

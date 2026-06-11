package com.example.escuela.services;

import com.example.escuela.dto.inscripciones.InscripcionRequest;
import com.example.escuela.dto.inscripciones.InscripcionResponse;
import com.example.escuela.entities.Alumno;
import com.example.escuela.entities.Grupo;
import com.example.escuela.entities.Inscripcion;
import com.example.escuela.exceptions.EntidadRelacionadaException;
import com.example.escuela.mappers.InscripcionMapper;
import com.example.escuela.repositories.AlumnoRepository;
import com.example.escuela.repositories.CalificacionRepository;
import com.example.escuela.repositories.GrupoRepository;
import com.example.escuela.repositories.InscripcionRepository;
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
public class InscripcionServiceImpl implements InscripcionService {

    private final InscripcionRepository inscripcionRepository;

    private final AlumnoRepository alumnoRepository;

    private final GrupoRepository grupoRepository;

    private final InscripcionMapper inscripcionMapper;

    private final CalificacionRepository calificacionRepository;

    @Override
    public List<InscripcionResponse> listar() {

        log.info("Listado de todas las inscripciones solicitadas");
        return inscripcionRepository.findAll().stream()
                .map(inscripcionMapper::entidadAResponse).toList();
    }

    @Override
    public InscripcionResponse obtenerPorId(Long id) {
        return inscripcionMapper.entidadAResponse(obtenerInscripcion(id));
    }

    @Override
    public InscripcionResponse registrar(InscripcionRequest request) {
        log.info("Registrando nueva inscripcion...");

        Alumno alumno = ServiceUtils.obtenerEntidadOException(
                alumnoRepository, request.idAlumno(), Alumno.class);
        Grupo grupo = ServiceUtils.obtenerEntidadOException(
                grupoRepository, request.idGrupo(), Grupo.class);

        if (inscripcionRepository.existsByAlumnoIdAndGrupoId(request.idAlumno(), request.idGrupo()))
            throw new IllegalArgumentException("El alumno ya está inscrito en este grupo");

        Inscripcion inscripcion = inscripcionMapper.requestAEntidad(request);

        inscripcionRepository.save(inscripcion);

        log.info("Nueva inscripcion registrada con id {}", inscripcion.getId());
        return inscripcionMapper.entidadAResponse(inscripcion);
    }

    @Override
    public InscripcionResponse actualizar(InscripcionRequest request, Long id) {
        throw new UnsupportedOperationException("No se puede actualizar una inscripción");
    }

    @Override
    public void eliminar(Long id) {
        Inscripcion inscripcion = obtenerInscripcion(id);
        if (calificacionRepository.existsByInscripcionId(id))
            throw new EntidadRelacionadaException("No se puede eliminar una inscripción que tiene calificación");

        log.info("Eliminando inscripcion con id: {}", id);


        inscripcionRepository.delete(inscripcion);
        log.info("Inscripcion con id {} eliminado", id);
    }

    private Inscripcion obtenerInscripcion(Long id) {
        return ServiceUtils.obtenerEntidadOException(inscripcionRepository, id, Inscripcion.class);
    }
}

package com.example.escuela.services;

import com.example.escuela.dto.alumnos.AlumnoRequest;
import com.example.escuela.dto.alumnos.AlumnoResponse;
import com.example.escuela.entities.Alumno;
import com.example.escuela.exceptions.EntidadRelacionadaException;
import com.example.escuela.mappers.AlumnoMapper;
import com.example.escuela.repositories.AlumnoRepository;
import com.example.escuela.repositories.InscripcionRepository;
import com.example.escuela.utils.ServiceUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Scanner;


@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class AlumnoServiceImpl  implements AlumnoService{

    private final AlumnoRepository alumnoRepository;

    private final InscripcionRepository inscripcionRepository;

    private final AlumnoMapper alumnoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<AlumnoResponse> listar() {
        log.info("Listando todos los alumnos");
        return alumnoRepository.findAll().stream()
                .map(alumnoMapper::entidadAResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AlumnoResponse obtenerPorId(Long id) {
        return alumnoMapper.entidadAResponse(obtenerAlumno(id));
    }

    @Override
    public AlumnoResponse registrar(AlumnoRequest request) {
        log.info("Registrando nuevo alumno...");

        Alumno alumno = alumnoMapper.requestAEntidad(request);

        validarDatosUnicos(alumno.getEmail(), alumno.getMatricula());

        Alumno alumnoGuardado = alumnoRepository.save(alumno);
        log.info("Nuevo alumno {} registrado", alumnoGuardado.getNombre());
        return alumnoMapper.entidadAResponse(alumnoGuardado);
    }

    private void validarDatosUnicos(String email, String matricula) {
        if (alumnoRepository.existsByEmail(email))
            throw new IllegalArgumentException("Ya existe un alumno con el email: " + email);

        if (alumnoRepository.existsByMatricula(matricula))
            throw new IllegalArgumentException("Ya existe un alumno con la matricula: " + matricula);
    }

    @Override
    public AlumnoResponse actualizar(AlumnoRequest request, Long id) {
        Alumno alumno = obtenerAlumno(id);

        log.info("Actualizando alumno con id: {}", id);

        String email = alumnoMapper.generarEmail(request.nombre().trim(), request.apellidoPaterno().trim());
        String matricula = alumno.getMatricula();

        validarCambiosUnicos(email, matricula, id);

        alumno.actualizar(
                request.nombre().trim(),
                request.apellidoPaterno().trim(),
                request.apellidoMaterno().trim(),
                email
        );

        log.info("Alumno {} actualizado", alumno.getNombre());
        return alumnoMapper.entidadAResponse(alumno);
    }

    private void validarCambiosUnicos(String email, String matricula, Long id) {
        if (alumnoRepository.existsByEmailAndIdNot(email, id))
            throw new IllegalArgumentException("Ya existe un alumno con el email: " + email);

        if (alumnoRepository.existsByMatriculaAndIdNot(matricula, id))
            throw new IllegalArgumentException("Ya existe un alumno con la matricula: " + matricula);
    }

    @Override
    public void eliminar(Long id) {
        Alumno alumno = obtenerAlumno(id);

        log.info("Eliminando alumno con id: {}", id);

        if (inscripcionRepository.existsByAlumnoId(id))
            throw new EntidadRelacionadaException("No se puede eliminar un alumno que tiene inscripciones asociadas");

        alumnoRepository.delete(alumno);
        log.info("Alumno con id {} eliminado", id);
    }

    private Alumno obtenerAlumno(Long id) {
        return ServiceUtils.obtenerEntidadOException(alumnoRepository, id, Alumno.class);
    }
}

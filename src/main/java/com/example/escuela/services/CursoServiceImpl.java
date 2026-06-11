package com.example.escuela.services;

import com.example.escuela.dto.cursos.CursoRequest;
import com.example.escuela.dto.cursos.CursoResponse;
import com.example.escuela.entities.Curso;
import com.example.escuela.exceptions.EntidadRelacionadaException;
import com.example.escuela.mappers.CursoMapper;
import com.example.escuela.repositories.CursoRepository;
import com.example.escuela.repositories.GrupoRepository;
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
public class CursoServiceImpl  implements  CursoService{

    private final CursoRepository cursoRepository;

    private final GrupoRepository grupoRepository;

    private final CursoMapper cursoMapper;

    @Override
    public List<CursoResponse> listar() {
        log.info("Listando todos los cursos");
        return  cursoRepository.findAll().stream()
                .map(cursoMapper::entidadAResponse).toList();
    }

    @Override
    public CursoResponse obtenerPorId(Long id) {
        return cursoMapper.entidadAResponse(obtenerCurso(id));
    }

    @Override
    public CursoResponse registrar(CursoRequest request) {
      log.info("Registrando un nuevo curso");

      Curso curso = cursoMapper.requestAEntidad(request);

      validarDatosUnicos(curso.getNombre());

      Curso cursoGuardado = cursoRepository.save(curso);
      log.info("Nuevo curso {} registrado", cursoGuardado.getNombre());
      return cursoMapper.entidadAResponse(cursoGuardado);
    }

    private void validarDatosUnicos(String nombre) {
        if (cursoRepository.existsByNombreIgnoreCase(nombre))
            throw new IllegalArgumentException("Ya existe un curso con el nombre: " + nombre);
    }

    @Override
    public CursoResponse actualizar(CursoRequest request, Long id) {
        Curso curso = obtenerCurso(id);

        log.info("Actualizando un curso con id: {} ", id);

        validarCambiosUnicos(request.nombre().trim(), id);

        curso.actualizar(
                request.nombre().trim(),
                request.descripcion().trim(),
                request.creditos()
        );

        log.info("Curso {} actualizado", curso.getNombre());
        return  cursoMapper.entidadAResponse(curso);
    }

    private void validarCambiosUnicos(String nombre, Long id) {
        if (cursoRepository.existsByNombreIgnoreCaseAndIdNot(nombre, id))
            throw new IllegalArgumentException("Ya existe un curso con el nombre: " + nombre);
    }

    @Override
    public void eliminar(Long id) {

        Curso curso = obtenerCurso(id);

        if (grupoRepository.existsByCursoId(id))
            throw new EntidadRelacionadaException("No se puede eliminar el curso ");

        log.info("Eliminando curso con id {} ", id);
        cursoRepository.delete(curso);
        log.info("Curso eliminado con id {} ", id);

    }

    private Curso obtenerCurso(Long id) {
        return ServiceUtils.obtenerEntidadOException(cursoRepository, id, Curso.class);
    }


}

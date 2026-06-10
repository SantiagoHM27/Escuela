package com.example.escuela.services;

import com.example.escuela.dto.cursos.CursoRequest;
import com.example.escuela.dto.cursos.CursoResponse;
import com.example.escuela.entities.Curso;
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
        return List.of();
    }

    @Override
    public CursoResponse obtenerPorId(Long id) {
        return null;
    }

    @Override
    public CursoResponse registrar(CursoRequest request) {
        return null;
    }

    @Override
    public CursoResponse actualizar(CursoRequest request, Long id) {
        return null;
    }

    @Override
    public void eliminar(Long id) {

    }

    private Curso obtenerCurso(Long id) {
        return ServiceUtils.obtenerEntidadOException(cursoRepository, id, Curso.class);
    }
}

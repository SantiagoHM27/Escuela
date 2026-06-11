package com.example.escuela.services;

import com.example.escuela.dto.aulas.AulaRequest;
import com.example.escuela.dto.aulas.AulaResponse;
import com.example.escuela.entities.Aula;
import com.example.escuela.exceptions.EntidadRelacionadaException;
import com.example.escuela.mappers.AulaMapper;
import com.example.escuela.repositories.AulaRepository;
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
public class AulaServiceImpl implements AulaService {

    private final AulaRepository aulaRepository;
    private final GrupoRepository grupoRepository;
    private final AulaMapper aulaMapper;

    @Override
    @Transactional(readOnly = true)
    public List<AulaResponse> listar() {
        log.info("Listando todas las aulas");
        return aulaRepository.findAll().stream()
                .map(aulaMapper::entidadAResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AulaResponse obtenerPorId(Long id) {
        return aulaMapper.entidadAResponse(obtenerAula(id));
    }

    @Override
    public AulaResponse registrar(AulaRequest request) {
        log.info("Registrando nueva aula...");

        if (aulaRepository.existsByNombreIgnoreCase(request.nombre().trim()))
            throw new IllegalArgumentException("Ya existe un aula con el nombre: " + request.nombre());

        Aula aula = aulaMapper.requestAEntidad(request);
        Aula aulaGuardada = aulaRepository.save(aula);

        log.info("Nueva aula {} registrada", aulaGuardada.getNombre());
        return aulaMapper.entidadAResponse(aulaGuardada);
    }

    @Override
    public AulaResponse actualizar(AulaRequest request, Long id) {
        Aula aula = obtenerAula(id);

        log.info("Actualizando aula con id: {}", id);

        if (aulaRepository.existsByNombreIgnoreCaseAndIdNot(request.nombre().trim(), id))
            throw new IllegalArgumentException("Ya existe un aula con el nombre: " + request.nombre());

        aula.actualizar(request.nombre().trim(), request.capacidad());

        log.info("Aula {} actualizada", aula.getNombre());
        return aulaMapper.entidadAResponse(aula);
    }

    @Override
    public void eliminar(Long id) {
        Aula aula = obtenerAula(id);

        log.info("Eliminando aula con id: {}", id);

        if (grupoRepository.existsByAulaId(id))
            throw new EntidadRelacionadaException("No se puede eliminar un aula que tiene grupos asignados");

        aulaRepository.delete(aula);
        log.info("Aula con id {} eliminada", id);
    }

    private Aula obtenerAula(Long id) {
        return ServiceUtils.obtenerEntidadOException(aulaRepository, id, Aula.class);
    }
}
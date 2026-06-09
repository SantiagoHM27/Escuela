package com.example.escuela.services;

import com.example.escuela.dto.maestros.MaestroRequest;
import com.example.escuela.dto.maestros.MaestroResponse;

import java.util.List;

public interface CrudService <RQ, RS>{
    List<RS> listar();

    RS obtenerPorId(Long id);

    RS registrar(RQ request);

    RS actualizar(RQ request, Long id);

    MaestroResponse actualizar(MaestroRequest request);

    void eliminar(Long id);
}

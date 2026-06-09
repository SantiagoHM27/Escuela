package com.example.escuela.services;

import com.example.escuela.dto.maestros.MaestroRequest;
import com.example.escuela.dto.maestros.MaestroResponse;

import java.util.List;

public interface MaestroService  extends CrudService<MaestroRequest, MaestroResponse>{
    @Override
    default List<MaestroResponse> listar() {
        return List.of();
    }

    @Override
    default MaestroResponse obtenerPorId(Long id) {
        return null;
    }

    @Override
    default MaestroResponse registrar(MaestroRequest request) {
        return null;
    }

    @Override
    default MaestroResponse actualizar(MaestroRequest request) {
        return null;
    }

    @Override
    default void eliminar(Long id) {

    }
}

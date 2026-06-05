package com.example.escuela.services;

import java.util.List;

public interface CrudService <RQ, RS>{
    List<RS> listar();

    RS obtenerPorId(Long id);

    RS registrar(RQ request);

    RS actualizar(RQ request);

    void eliminar(Long id);
}

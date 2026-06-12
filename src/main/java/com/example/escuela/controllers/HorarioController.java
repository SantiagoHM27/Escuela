package com.example.escuela.controllers;

import com.example.escuela.dto.horarios.HorarioRequest;
import com.example.escuela.dto.horarios.HorarioResponse;
import com.example.escuela.services.HorarioService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/horarios")

public class HorarioController extends CommonController<HorarioRequest, HorarioResponse, HorarioService> {

    public HorarioController(HorarioService service) {
        super(service);
    }
}
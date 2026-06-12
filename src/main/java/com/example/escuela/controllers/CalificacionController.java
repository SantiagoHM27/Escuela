package com.example.escuela.controllers;


import com.example.escuela.dto.calificaciones.CalificacionRequest;
import com.example.escuela.dto.calificaciones.CalificacionResponse;
import com.example.escuela.services.CalificacionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/calificaciones")

public class CalificacionController  extends  CommonController<CalificacionRequest, CalificacionResponse, CalificacionService>{

    public CalificacionController (CalificacionService service){super(service);}
}

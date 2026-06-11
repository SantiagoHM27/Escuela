package com.example.escuela.controllers;


import com.example.escuela.dto.inscripciones.InscripcionRequest;
import com.example.escuela.dto.inscripciones.InscripcionResponse;
import com.example.escuela.services.InscripcionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionController  extends  CommonController<InscripcionRequest, InscripcionResponse, InscripcionService>{

    public InscripcionController (InscripcionService service){super (service);}
}

package com.example.escuela.controllers;

import com.example.escuela.dto.aulas.AulaRequest;
import com.example.escuela.dto.aulas.AulaResponse;
import com.example.escuela.services.AulaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/aulas")
public class AulaController  extends CommonController<AulaRequest, AulaResponse, AulaService>{
    public AulaController(AulaService service) {super(service);}
}

package com.example.escuela.controllers;

import com.example.escuela.dto.grupos.GrupoRequest;
import com.example.escuela.dto.grupos.GrupoResponse;
import com.example.escuela.services.GrupoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/grupos")
public class GrupoController extends CommonController<GrupoRequest, GrupoResponse, GrupoService> {

    public GrupoController (GrupoService service){super(service);}
}

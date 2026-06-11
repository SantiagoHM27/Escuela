package com.example.escuela.controllers;


import com.example.escuela.dto.cursos.CursoRequest;
import com.example.escuela.dto.cursos.CursoResponse;
import com.example.escuela.entities.Curso;
import com.example.escuela.services.CursoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cursos")
public class CursoController  extends CommonController<CursoRequest, CursoResponse, CursoService>{
    public CursoController (CursoService service) {super (service);}
}

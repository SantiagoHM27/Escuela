package com.example.escuela.controllers;

import com.example.escuela.dto.alumnos.AlumnoRequest;
import com.example.escuela.dto.alumnos.AlumnoResponse;
import com.example.escuela.entities.Alumno;
import com.example.escuela.services.AlumnoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/alumnos")
public class AlumnoController extends CommonController<AlumnoRequest, AlumnoResponse, AlumnoService>{

    public AlumnoController (AlumnoService service) {super (service);}
}

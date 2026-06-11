package com.example.escuela.mappers;

import com.example.escuela.dto.datos.DatosAlumno;
import com.example.escuela.dto.datos.DatosGrupo;
import com.example.escuela.dto.inscripciones.InscripcionRequest;
import com.example.escuela.dto.inscripciones.InscripcionResponse;
import com.example.escuela.entities.Alumno;
import com.example.escuela.entities.Grupo;
import com.example.escuela.entities.Inscripcion;
import com.example.escuela.repositories.AlumnoRepository;
import com.example.escuela.repositories.GrupoRepository;
import com.example.escuela.utils.ServiceUtils;
import com.example.escuela.utils.StringCustomUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@AllArgsConstructor
public class InscripcionMapper implements  CommonMapper<InscripcionRequest, InscripcionResponse, Inscripcion> {

    private final AlumnoRepository alumnoRepository;

    private final GrupoRepository grupoRepository;

    @Override
    public Inscripcion requestAEntidad(InscripcionRequest request) {
        if (request == null) return null;

        Alumno alumno = ServiceUtils.obtenerEntidadOException(
                alumnoRepository, request.idAlumno(), Alumno.class);
        Grupo grupo = ServiceUtils.obtenerEntidadOException(
                grupoRepository, request.idGrupo(), Grupo.class);

        return Inscripcion.builder()
                .alumno(alumno)
                .grupo(grupo)
                .fechaInscripcion(LocalDate.now())
                .build();
    }

    @Override
    public InscripcionResponse entidadAResponse(Inscripcion entidad) {
        if (entidad == null) return null;

        Alumno alumno = entidad.getAlumno();
        Grupo grupo = entidad.getGrupo();

        return new InscripcionResponse(
                entidad.getId(),
                new DatosAlumno(
                        String.join(" ",
                                alumno.getNombre(),
                                alumno.getApellidoPaterno(),
                                alumno.getApellidoMaterno()),
                        alumno.getMatricula(),
                        alumno.getEmail(),
                        StringCustomUtils.localDateAString(alumno.getFechaIngreso())
                ),
                new DatosGrupo(
                        grupo.getCurso().getNombre(),
                        String.join(" ",
                                grupo.getMaestro().getNombre(),
                                grupo.getMaestro().getApellidoPaterno(),
                                grupo.getMaestro().getApellidoMaterno()),
                        grupo.getAula().getNombre(),
                        grupo.getPeriodo()
                ),
                StringCustomUtils.localDateAString(entidad.getFechaInscripcion()),
                entidad.getCalificacion() == null ? null :
                        entidad.getCalificacion().getCalificacion()
        );
    }
}
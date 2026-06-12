package com.example.escuela.mappers;


import com.example.escuela.dto.calificaciones.CalificacionRequest;
import com.example.escuela.dto.calificaciones.CalificacionResponse;
import com.example.escuela.dto.datos.DatosAlumno;
import com.example.escuela.dto.datos.DatosGrupo;
import com.example.escuela.dto.datos.DatosInscripcion;
import com.example.escuela.entities.Alumno;
import com.example.escuela.entities.Calificacion;
import com.example.escuela.entities.Grupo;
import com.example.escuela.entities.Inscripcion;
import com.example.escuela.repositories.InscripcionRepository;
import com.example.escuela.utils.ServiceUtils;
import com.example.escuela.utils.StringCustomUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CalificacionMapper implements  CommonMapper<CalificacionRequest, CalificacionResponse, Calificacion> {

    private final InscripcionRepository inscripcionRepository;

    @Override
    public Calificacion requestAEntidad(CalificacionRequest request) {
        if (request == null) return null;

        Inscripcion inscripcion = ServiceUtils.obtenerEntidadOException(
                inscripcionRepository, request.idInscripcion(), Inscripcion.class);
        return Calificacion.builder()
                .inscripcion(inscripcion)
                .build();
    }

    @Override
    public CalificacionResponse entidadAResponse(Calificacion entidad) {
        if (entidad == null) return null;

        Inscripcion inscripcion = entidad.getInscripcion();
        Alumno alumno = inscripcion.getAlumno();
        Grupo grupo = inscripcion.getGrupo();

        return new CalificacionResponse(
                entidad.getId(),
                new DatosInscripcion(
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
                        StringCustomUtils.localDateAString(inscripcion.getFechaInscripcion())
                ),
                entidad.getCalificacion(),
                StringCustomUtils.localDateAString(entidad.getFechaRegistro())
        );
    }

}

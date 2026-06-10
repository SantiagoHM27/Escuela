package com.example.escuela.mappers;

import com.example.escuela.dto.alumnos.AlumnoRequest;
import com.example.escuela.dto.alumnos.AlumnoResponse;
import com.example.escuela.dto.datos.DatosCalificacionAlumno;
import com.example.escuela.entities.Alumno;
import com.example.escuela.repositories.AlumnoRepository;
import com.example.escuela.utils.StringCustomUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Component
@AllArgsConstructor
public class AlumnoMapper implements CommonMapper<AlumnoRequest, AlumnoResponse, Alumno> {

    private final AlumnoRepository alumnoRepository;


    @Override
    public Alumno requestAEntidad(AlumnoRequest request) {
        if (request == null) return null;

        String nombre = request.nombre().trim();
        String apellidoPaterno = request.apellidoPaterno().trim();
        String apellidoMaterno = request.apellidoMaterno().trim();

        String email = generarEmail(nombre, apellidoPaterno);
        String matricula = generarMatricula();

        return Alumno.builder()
                .nombre(nombre)
                .apellidoPaterno(apellidoPaterno)
                .apellidoMaterno(apellidoMaterno)
                .email(email)
                .matricula(matricula)
                .build();
    }
    public String generarEmail(String nombre, String apellidoPaterno) {
        String n = StringCustomUtils.quitarAcentos(nombre.split(" ")[0]).toLowerCase();
        String ap = StringCustomUtils.quitarAcentos(apellidoPaterno).toLowerCase();
        return n + "." + ap + "@alumnos.com";
    }
    private String generarMatricula() {
        int anio = LocalDate.now().getYear();
        long total = alumnoRepository.count() + 1;
        return String.format("A%d%03d", anio, total);
    }

    @Override
    public AlumnoResponse entidadAResponse(Alumno entidad) {
        if (entidad == null) return null;

        List<DatosCalificacionAlumno> calificaciones = entidad.getInscripciones().stream()
                .map(inscripcion -> new DatosCalificacionAlumno(
                        inscripcion.getGrupo().getCurso().getNombre(),
                        inscripcion.getGrupo().getPeriodo(),
                        inscripcion.getCalificacion() == null ? null :
                                inscripcion.getCalificacion().getCalificacion()
                )).toList();

        BigDecimal promedio = calificaciones.stream()
                .map(DatosCalificacionAlumno::calificacion)
                .filter(c -> c != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        long total = calificaciones.stream()
                .filter(c -> c.calificacion() != null)
                .count();

        promedio = total > 0 ? promedio.divide(BigDecimal.valueOf(total), 2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        return new AlumnoResponse(
                entidad.getId(),
                String.join(" ",
                        entidad.getNombre(),
                        entidad.getApellidoPaterno(),
                        entidad.getApellidoMaterno()),
                entidad.getEmail(),
                entidad.getMatricula(),
                StringCustomUtils.localDateAString(entidad.getFechaIngreso()),
                calificaciones,
                promedio
        );
}

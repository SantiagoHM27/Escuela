package com.example.escuela.repositories;

import com.example.escuela.entities.Horario;
import com.example.escuela.enums.DiaSemana;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long> {

    boolean existsByGrupoId(Long grupoId);

    boolean existsByGrupoIdAndDiaSemanaAndHoraInicioLessThanAndHoraFinGreaterThan(
            Long grupoId, DiaSemana diaSemana, String horaFin, String horaInicio);

    boolean existsByGrupoAulaIdAndDiaSemanaAndHoraInicioLessThanAndHoraFinGreaterThan(
            Long aulaId, DiaSemana diaSemana, String horaFin, String horaInicio);
}
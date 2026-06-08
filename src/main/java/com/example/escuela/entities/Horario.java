package com.example.escuela.entities;

import com.example.escuela.entities.Grupo;
import com.example.escuela.enums.DiaSemana;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "HORARIOS")
@Builder
public class Horario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_HORARIO")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_GRUPO", nullable = false)
    private Grupo grupo;

    @Enumerated(EnumType.STRING)
    @Column(name = "DIA", nullable = false)
    private DiaSemana diaSemana;

    @Column(name = "HORA_INICIO", length = 5, nullable = false)
    private String horaInicio;

    @Column(name = "HORA_FIN", length = 5, nullable = false)
    private String horaFin;
}
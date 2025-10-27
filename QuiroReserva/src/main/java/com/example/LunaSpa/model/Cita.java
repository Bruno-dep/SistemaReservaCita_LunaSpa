package com.example.LunaSpa.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "citas")
@Data
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    @NotNull(message = "Debe seleccionar un paciente")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    @NotNull(message = "Debe seleccionar un doctor")
    private Doctor doctor;

    @NotNull(message = "Debe seleccionar fecha y hora")
    @FutureOrPresent(message = "La fecha debe ser actual o futura")
    private LocalDateTime fechaHora;

    @NotNull(message = "La duración es obligatoria")
    private Integer duracion; // En minutos

    @NotNull(message = "El estado es obligatorio")
    private String estado; // PENDIENTE, CONFIRMADA, CANCELADA

    private String notas; // Opcional
}
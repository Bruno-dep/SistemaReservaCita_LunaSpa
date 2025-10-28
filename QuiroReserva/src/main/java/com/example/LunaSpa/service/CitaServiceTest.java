package com.example.LunaSpa.service;

import com.example.LunaSpa.model.Cita;
import com.example.LunaSpa.model.Doctor;
import com.example.LunaSpa.model.Paciente;
import com.example.LunaSpa.repository.CitaRepository;
import com.example.LunaSpa.service.impl.CitaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CitaServiceTest {

    @Mock
    private CitaRepository citaRepository;

    @InjectMocks
    private CitaServiceImpl citaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGuardarCita_ConConflicto() {
        Cita cita = new Cita();
        cita.setDoctor(new Doctor());
        cita.setFechaHora(LocalDateTime.now());
        cita.setDuracion(30);
        cita.setEstado("PENDIENTE");

        when(citaRepository.findByDoctorAndFechaHora(any(), any()))
                .thenReturn(Collections.singletonList(cita));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> citaService.guardar(cita));

        assertEquals("El doctor ya tiene una cita en ese horario.", exception.getMessage());
        verify(citaRepository, never()).save(any());
    }

    @Test
    void testGuardarCita_SinConflicto() {
        Cita cita = new Cita();
        cita.setPaciente(new Paciente());
        cita.setDoctor(new Doctor());
        cita.setFechaHora(LocalDateTime.now());
        cita.setDuracion(30);
        cita.setEstado("PENDIENTE");

        when(citaRepository.findByDoctorAndFechaHora(any(), any()))
                .thenReturn(Collections.emptyList());
        when(citaRepository.save(any(Cita.class))).thenReturn(cita);

        Cita resultado = citaService.guardar(cita);
        assertNotNull(resultado);
        verify(citaRepository).save(any(Cita.class));
    }

    @Test
    void testBuscarPorId_Existente() {
        Cita cita = new Cita();
        cita.setId(1L);
        when(citaRepository.findById(1L)).thenReturn(Optional.of(cita));

        Cita resultado = citaService.buscarPorId(1L);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void testBuscarPorId_NoExistente() {
        when(citaRepository.findById(1L)).thenReturn(Optional.empty());

        assertNull(citaService.buscarPorId(1L));
    }
}

package com.example.QuiroReserva.service;

import com.example.QuiroReserva.model.Paciente;
import java.util.List;

public interface PacienteService {
    Paciente buscarPorId(Long id);
    List<Paciente> listarTodos();
    Paciente guardar(Paciente paciente);
    void eliminar(Long id);
}




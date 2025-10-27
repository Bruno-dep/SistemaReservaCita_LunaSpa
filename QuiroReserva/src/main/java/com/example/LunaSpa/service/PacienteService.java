package com.example.LunaSpa.service;

import com.example.LunaSpa.model.Paciente;
import java.util.List;

public interface PacienteService {
    Paciente buscarPorId(Long id);
    List<Paciente> listarTodos();
    Paciente guardar(Paciente paciente);
    void eliminar(Long id);
}




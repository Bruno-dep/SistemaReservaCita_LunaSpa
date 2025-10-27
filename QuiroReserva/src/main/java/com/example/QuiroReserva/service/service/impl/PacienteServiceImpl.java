package com.example.QuiroReserva.service.service.impl;


import com.example.QuiroReserva.model.Paciente;
import com.example.QuiroReserva.repository.PacienteRepository;
import com.example.QuiroReserva.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteServiceImpl implements PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Override
    public List<Paciente> listarTodos() {
        return pacienteRepository.findAll();
    }

    @Override
    public Paciente guardar(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    @Override
    public void eliminar(Long id) {
        pacienteRepository.deleteById(id);
    }

    @Override
    public Paciente buscarPorId(Long id) {
        return pacienteRepository.findById(id).orElse(null);
    }

}

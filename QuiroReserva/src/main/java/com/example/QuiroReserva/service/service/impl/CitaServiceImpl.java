package com.example.QuiroReserva.service.service.impl;

import com.example.QuiroReserva.model.Cita;
import com.example.QuiroReserva.repository.CitaRepository;
import com.example.QuiroReserva.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CitaServiceImpl implements CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Override
    public List<Cita> listarTodas() {
        return citaRepository.findAll();
    }

    @Override
    public Cita guardar(Cita cita) {
        return citaRepository.save(cita);
    }

    @Override
    public void eliminar(Long id) {
        citaRepository.deleteById(id);
    }

    @Override
    public Cita buscarPorId(Long id) {
        return citaRepository.findById(id).orElse(null);
    }

}


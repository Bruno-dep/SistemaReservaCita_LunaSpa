package com.example.LunaSpa.service.service.impl;

import com.example.LunaSpa.model.Cita;
import com.example.LunaSpa.repository.CitaRepository;
import com.example.LunaSpa.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CitaServiceImpl implements CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Override
    public Cita guardar(Cita cita) {
        List<Cita> citasExistentes = citaRepository.findByDoctorAndFechaHora(cita.getDoctor(), cita.getFechaHora());
        if (!citasExistentes.isEmpty()) {
            throw new IllegalArgumentException("El doctor ya tiene una cita en ese horario.");
        }
        return citaRepository.save(cita);
    }

    @Override
    public Cita buscarPorId(Long id) {
        return citaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Cita> listarTodas() {
        return citaRepository.findAll();
    }

    @Override
    public void eliminar(Long id) {
        citaRepository.deleteById(id);
    }
}
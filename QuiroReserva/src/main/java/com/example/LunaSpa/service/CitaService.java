package com.example.LunaSpa.service;

import com.example.LunaSpa.model.Cita;
import java.util.List;

public interface CitaService {
    Cita guardar(Cita cita);
    Cita buscarPorId(Long id);
    List<Cita> listarTodas();
    void eliminar(Long id);
}
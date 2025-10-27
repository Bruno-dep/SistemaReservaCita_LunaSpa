package com.example.QuiroReserva.service;

import com.example.QuiroReserva.model.Cita;
import java.util.List;

public interface CitaService {
    Cita buscarPorId(Long id);
    List<Cita> listarTodas();
    Cita guardar(Cita cita);
    void eliminar(Long id);
}




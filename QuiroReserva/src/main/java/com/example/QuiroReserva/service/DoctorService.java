package com.example.QuiroReserva.service;

import com.example.QuiroReserva.model.Doctor;
import java.util.List;

public interface DoctorService {
    List<Doctor> listarTodos();
    Doctor guardar(Doctor doctor);
    Doctor buscarPorId(Long id);
    void eliminar(Long id);
}




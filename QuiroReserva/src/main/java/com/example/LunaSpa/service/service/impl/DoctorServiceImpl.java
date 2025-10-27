package com.example.LunaSpa.service.service.impl;

import com.example.LunaSpa.model.Doctor;
import com.example.LunaSpa.repository.DoctorRepository;
import com.example.LunaSpa.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public List<Doctor> listarTodos() {
        return doctorRepository.findAll();
    }

    @Override
    public Doctor guardar(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor buscarPorId(Long id) {
        return doctorRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminar(Long id) {
        doctorRepository.deleteById(id);
    }
}


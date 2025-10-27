package com.example.QuiroReserva.repository;

import com.example.QuiroReserva.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}

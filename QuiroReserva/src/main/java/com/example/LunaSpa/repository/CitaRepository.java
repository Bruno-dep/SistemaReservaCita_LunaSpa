package com.example.LunaSpa.repository;

import com.example.LunaSpa.model.Cita;
import com.example.LunaSpa.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
    @Query("SELECT c FROM Cita c WHERE c.doctor = :doctor AND c.fechaHora = :fechaHora")
    List<Cita> findByDoctorAndFechaHora(Doctor doctor, LocalDateTime fechaHora);
}
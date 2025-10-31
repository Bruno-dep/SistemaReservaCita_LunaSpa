package com.example.LunaSpa.repository;

import com.example.LunaSpa.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}

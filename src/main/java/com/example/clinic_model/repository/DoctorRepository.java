package com.example.clinic_model.repository;

import com.example.clinic_model.model.Admin;
import com.example.clinic_model.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor,Long> {
    Optional<Doctor> findByUsername(String username);
    @Query("SELECT COUNT(*) FROM Doctor")
    Integer countAllDoctor();

    Integer countByIsVerifiedTrue();

}

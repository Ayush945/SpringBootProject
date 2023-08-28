package com.example.clinic_model.repository;

import com.example.clinic_model.model.Admin;
import com.example.clinic_model.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient,Long> {
    Optional<Patient> findByUsername(String username);
    boolean existsByOtp(int Otp);
    Optional<Patient> findByOtp(int Otp);

    @Query("SELECT COUNT(*) FROM Patient WHERE isVerified=true ")
    Integer countAllPatient();
    List<Patient>findByPatientNameStartingWith(String firstChar);
}

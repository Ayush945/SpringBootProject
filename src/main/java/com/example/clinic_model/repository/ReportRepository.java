package com.example.clinic_model.repository;

import com.example.clinic_model.model.Image;
import com.example.clinic_model.model.Patient;
import com.example.clinic_model.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report,Long> {

    Optional<Report> findByPatientPatientId(Long patientId);
}

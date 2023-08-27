package com.example.clinic_model.repository;

import com.example.clinic_model.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {
    Optional<Image> findByPatientPatientId(Long patientId);
    Optional<Image> findByDoctorDoctorId(Long doctorId);

    Optional<Image>findByReportReportId(Long reportId);
}

package com.example.clinic_model.repository;

import com.example.clinic_model.dto.ImageDTO;
import com.example.clinic_model.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {
    Optional<Image> findByPatientPatientId(Long patientId);
    Optional<Image> findByDoctorDoctorId(Long doctorId);
    Optional<Image> findByNewsNewsId(Long newsId);
    Optional<Image>findByReportReportId(Long reportId);
    boolean existsByNewsNewsId(Long newsId);
    boolean existsByPatientPatientId(Long patientId);
    boolean existsByDoctorDoctorId(Long doctorId);


}

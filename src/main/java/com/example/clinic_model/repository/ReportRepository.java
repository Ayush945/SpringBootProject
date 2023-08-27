package com.example.clinic_model.repository;

import com.example.clinic_model.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report,Long> {
}

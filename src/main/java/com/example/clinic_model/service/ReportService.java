package com.example.clinic_model.service;

import com.example.clinic_model.dto.ReportDTO;

import java.util.List;

public interface ReportService {
    ReportDTO createReport(Long patientId);

    ReportDTO createReportForPatientAndDoctor(ReportDTO reportDTO, Long patientId, Long doctorId);

    List<ReportDTO> getAllReports();
    ReportDTO getReportById(Long reportId);

    ReportDTO updateReport(Long reportId, ReportDTO reportDTO);
    void deleteReport(Long reportId);
}

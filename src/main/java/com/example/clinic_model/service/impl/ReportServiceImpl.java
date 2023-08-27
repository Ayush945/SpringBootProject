package com.example.clinic_model.service.impl;

import com.example.clinic_model.dto.DoctorDTO;
import com.example.clinic_model.dto.PatientDTO;
import com.example.clinic_model.dto.ReportDTO;
import com.example.clinic_model.exception.ResourceNotFoundException;
import com.example.clinic_model.model.Doctor;
import com.example.clinic_model.model.Patient;
import com.example.clinic_model.model.Report;
import com.example.clinic_model.repository.ReportRepository;
import com.example.clinic_model.service.DoctorService;
import com.example.clinic_model.service.PatientService;
import com.example.clinic_model.service.ReportService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Override
    public List<ReportDTO> getAllReports() {
        List<Report> reports = reportRepository.findAll();
        return reports.stream()
                .map(report -> modelMapper.map(report, ReportDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ReportDTO getReportById(Long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new ResourceNotFoundException("Report not found"));
        return modelMapper.map(report, ReportDTO.class);
    }

    @Override
    public ReportDTO createReport(ReportDTO reportDTO) {
        Report report = modelMapper.map(reportDTO, Report.class);
        report = reportRepository.save(report);
        return modelMapper.map(report, ReportDTO.class);
    }

    @Override
    public ReportDTO createReportForPatientAndDoctor(ReportDTO reportDTO, Long patientId, Long doctorId) {
        Report report = this.modelMapper.map(reportDTO, Report.class);

        PatientDTO patientDTO = this.patientService.getPatientById(patientId);
        Patient patient = this.modelMapper.map(patientDTO, Patient.class);

        DoctorDTO doctorDTO = this.doctorService.getDoctorById(doctorId);
        Doctor doctor = this.modelMapper.map(doctorDTO, Doctor.class);

        report.setPatient(patient);
        report.setDoctor(doctor);

        Report savedReport = this.reportRepository.save(report);

        return this.modelMapper.map(report, ReportDTO.class);
    }

    @Override
    public ReportDTO updateReport(Long reportId, ReportDTO reportDTO) {
        Optional<Report> optionalReport = reportRepository.findById(reportId);

        if (optionalReport.isEmpty()) {
            throw new ResourceNotFoundException("Report not found");
        }

        Report existingReport = optionalReport.get();

        // Update properties from DTO
        if (reportDTO.getReportDate() != null) {
            existingReport.setReportDate(reportDTO.getReportDate());
        }
        if (reportDTO.getReportFile() != null) {
            existingReport.setReportFile(reportDTO.getReportFile());
        }

        // Save the updated report
        Report updatedReport = reportRepository.save(existingReport);

        return modelMapper.map(updatedReport, ReportDTO.class);
    }
    @Override
    public void deleteReport(Long reportId) {
        if (!reportRepository.existsById(reportId)) {
            throw new ResourceNotFoundException("Report not found");
        }
        reportRepository.deleteById(reportId);
    }
}

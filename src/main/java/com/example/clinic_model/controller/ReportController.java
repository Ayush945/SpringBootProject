package com.example.clinic_model.controller;

import com.example.clinic_model.dto.ReportDTO; // Import the ReportDTO class
import com.example.clinic_model.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public List<ReportDTO> getAllReports() {
        return reportService.getAllReports();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportDTO> getReportById(@PathVariable Long id) {
        ReportDTO reportDTO = reportService.getReportById(id);
        return ResponseEntity.ok(reportDTO);
    }

//    @PostMapping
//    public ResponseEntity<ReportDTO> createReport(@RequestBody ReportDTO reportDTO) {
//        ReportDTO createdReport = reportService.createReport(reportDTO);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdReport);
//    }

    @PostMapping("/create-report/patient/{patientId}/doctor/{doctorId}")
    public ResponseEntity<ReportDTO> createReportForPatientAndDoctor(@Valid @RequestBody ReportDTO reportDTO,
                                                                     @PathVariable("patientId") Long patientId,
                                                                     @PathVariable("doctorId") Long doctorId)
    {
        ReportDTO createdReport = this.reportService.createReportForPatientAndDoctor(reportDTO, patientId, doctorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReport);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReportDTO> updateReport(@PathVariable Long id, @RequestBody ReportDTO reportDTO) {
        ReportDTO updatedReport = reportService.updateReport(id, reportDTO);
        return ResponseEntity.ok(updatedReport);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        reportService.deleteReport(id);
        return ResponseEntity.noContent().build();
    }
}

package com.example.clinic_model.controller;

import com.example.clinic_model.dto.ImageDTO;
import com.example.clinic_model.dto.ImageDownloadDTO;
import com.example.clinic_model.dto.ReportDTO; // Import the ReportDTO class
import com.example.clinic_model.service.FileService;
import com.example.clinic_model.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
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
    private FileService fileService;
    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public List<ReportDTO> getAllReports() {
        return reportService.getAllReports();
    }

    @GetMapping("/{reportId}")
    public ResponseEntity<ReportDTO> getReportById(@PathVariable Long reportId) {
        ReportDTO reportDTO = reportService.getReportById(reportId);
        return ResponseEntity.ok(reportDTO);
    }

    @PostMapping("/create-report/{patientId}")
    public ResponseEntity<ReportDTO> createReport(@PathVariable("patientId") Long patientId) {
        ReportDTO createdReport = reportService.createReport(patientId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReport);
    }

    //upload report pic
    @PostMapping("/upload-report-pic/{reportId}")
    ResponseEntity<ImageDTO>uploadReportPic(@PathVariable("reportId")Long reportId,@ModelAttribute ImageDTO imageDTO){
        return ResponseEntity.ok().body(fileService.uploadReportPic(reportId,imageDTO));
    }

    @GetMapping("/get-report-pic/{patientId}")
    ResponseEntity<Resource> getProfilePic(@PathVariable("patientId") Long patientId){
        ImageDownloadDTO imageDownloadDTO=this.fileService.getReportPic(patientId);
        return ResponseEntity.ok()
                .contentType(imageDownloadDTO.getMediaType())
                .body(imageDownloadDTO.getResource());
    }
}
package com.example.clinic_model.controller;

import com.example.clinic_model.dto.ImageDTO;
import com.example.clinic_model.dto.ImageDownloadDTO;
import com.example.clinic_model.dto.PatientDTO; // Import the PatientDTO class
import com.example.clinic_model.service.FileService;
import com.example.clinic_model.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {
    @Autowired
    private  FileService fileService;

    private final PatientService patientService;


    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }


    @PostMapping
    public ResponseEntity<PatientDTO> createPatient(@RequestBody PatientDTO patientDTO) {
        PatientDTO createdPatient = patientService.createPatient(patientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPatient);
    }

    @GetMapping
    public List<PatientDTO> getAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable Long id) {
        PatientDTO patientDTO = patientService.getPatientById(id);
        return ResponseEntity.ok(patientDTO);
    }
    //to update patient profile
    @PutMapping("/{id}")
    public ResponseEntity<PatientDTO> updatePatient(@PathVariable Long id, @RequestBody PatientDTO patientDTO) {
        PatientDTO updatedPatient = patientService.updatePatient(id, patientDTO);
        return ResponseEntity.ok(updatedPatient);
    }

    //to add patient profile pic
    @PostMapping("/upload-patient-profile-pic/{patientId}")
    ResponseEntity<ImageDTO> updatePatient(@PathVariable("patientId") Long patientId ,@ModelAttribute ImageDTO imageDTO){

        return  ResponseEntity.ok().body(fileService.uploadPatientProfilePic(patientId,imageDTO));
    }
    //get patient profile pic
    @GetMapping("/get-patient-profile-pic/{patientId}")
    ResponseEntity<Resource> getProfilePic(@PathVariable("patientId") Long patientId){
        ImageDownloadDTO imageDownloadDTO=this.fileService.getPatientProfilePic(patientId);
        return ResponseEntity.ok()
                .contentType(imageDownloadDTO.getMediaType())
                .body(imageDownloadDTO.getResource());
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatientById(id);
        return ResponseEntity.noContent().build();
    }
}

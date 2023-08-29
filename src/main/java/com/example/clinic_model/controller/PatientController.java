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
import org.springframework.security.access.prepost.PreAuthorize;
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



    @GetMapping("/get-all-patient")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<PatientDTO> getAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable Long patientId) {
        PatientDTO patientDTO = patientService.getPatientById(patientId);
        return ResponseEntity.ok(patientDTO);
    }
    //to update patient profile
    @PreAuthorize("hasAuthority('ROLE_PATIENT')")
    @PutMapping("/{patientId}")
    public ResponseEntity<PatientDTO> updatePatient(@PathVariable Long patientId, @RequestBody PatientDTO patientDTO) {
        PatientDTO updatedPatient = patientService.updatePatient(patientId, patientDTO);
        return ResponseEntity.ok(updatedPatient);
    }

    //to add patient profile pic
    @PreAuthorize("hasAuthority('ROLE_PATIENT')")
    @PostMapping("/upload-patient-profile-pic/{patientId}")
    ResponseEntity<ImageDTO> uploadPatientProfilePic(@PathVariable("patientId") Long patientId ,@ModelAttribute ImageDTO imageDTO){

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

    @GetMapping("/search-patient/{firstChar}")
    ResponseEntity<List<PatientDTO>>searchPatientStartingWith(@PathVariable("firstChar") String firstChar){
        return ResponseEntity.ok( ).body(patientService.searchPatientStartingWith(firstChar));
    }
}

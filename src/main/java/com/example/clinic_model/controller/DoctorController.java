package com.example.clinic_model.controller;

import com.example.clinic_model.dto.DoctorDTO; // Import the DoctorDTO class
import com.example.clinic_model.dto.ImageDTO;
import com.example.clinic_model.dto.ImageDownloadDTO;
import com.example.clinic_model.service.DoctorService;
import com.example.clinic_model.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    private final DoctorService doctorService;
    @Autowired
    private FileService fileService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping
    public ResponseEntity<DoctorDTO> createDoctor(@RequestBody DoctorDTO doctorDTO) {
        DoctorDTO createdDoctor = doctorService.createDoctor(doctorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDoctor);
    }

    @GetMapping
    public List<DoctorDTO> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> getDoctorById(@PathVariable Long id) {
        DoctorDTO doctorDTO = doctorService.getDoctorById(id);
        return ResponseEntity.ok(doctorDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorDTO> updateDoctor(@PathVariable Long id, @RequestBody DoctorDTO doctorDTO) {
        DoctorDTO updatedDoctor = doctorService.updateDoctor(id, doctorDTO);
        return ResponseEntity.ok(updatedDoctor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctorById(id);
        return ResponseEntity.noContent().build();
    }

    //upload doctor profile pic
    @PostMapping("/upload-doctor-profile-pic/{doctorId}")
    ResponseEntity<ImageDTO> updatePatient(@PathVariable("doctorId") Long doctorId , @ModelAttribute ImageDTO imageDTO){

        return  ResponseEntity.ok().body(fileService.uploadDoctorProfilePic(doctorId,imageDTO));
    }
    //get doctor profile pic
    @GetMapping("/get-doctor-profile-pic/{doctorId}")
    ResponseEntity<Resource> getProfilePic(@PathVariable("doctorId") Long doctorId){
        ImageDownloadDTO imageDownloadDTO=this.fileService.getDoctorProfilePic(doctorId);
        return ResponseEntity.ok()
                .contentType(imageDownloadDTO.getMediaType())
                .body(imageDownloadDTO.getResource());
    }
}

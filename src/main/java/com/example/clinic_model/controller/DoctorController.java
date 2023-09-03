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
import org.springframework.security.access.prepost.PreAuthorize;
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


    @GetMapping("list-all-doctor")
    public List<DoctorDTO> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @GetMapping("get-doctor/{doctorId}")
    public ResponseEntity<DoctorDTO> getDoctorById(@PathVariable Long doctorId) {
        DoctorDTO doctorDTO = doctorService.getDoctorById(doctorId);
        return ResponseEntity.ok(doctorDTO);
    }

    @PreAuthorize("hasAuthority('ROLE_DOCTOR')")
    @PutMapping("update-doctor/{doctorId}")
    public ResponseEntity<DoctorDTO> updateDoctor(@PathVariable Long doctorId, @RequestBody DoctorDTO doctorDTO) {
        DoctorDTO updatedDoctor = doctorService.updateDoctor(doctorId, doctorDTO);
        return ResponseEntity.ok(updatedDoctor);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{doctorId}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long doctorId) {
        doctorService.deleteDoctorById(doctorId);
        return ResponseEntity.noContent().build();
    }

    //upload doctor profile pic

    @PreAuthorize("hasAuthority('ROLE_DOCTOR')")
    @PostMapping("/upload-doctor-profile-pic/{doctorId}")
    ResponseEntity<ImageDTO> uploadDoctorProfilePic(@PathVariable("doctorId") Long doctorId , @ModelAttribute ImageDTO imageDTO){

        return  ResponseEntity.ok().body(fileService.uploadDoctorProfilePic(doctorId,imageDTO));
    }
    //get doctor profile pic
    @GetMapping("/get-doctor-profile-pic/{doctorId}")
    ResponseEntity<Resource> getDoctorProfilePic(@PathVariable("doctorId") Long doctorId){
        ImageDownloadDTO imageDownloadDTO=this.fileService.getDoctorProfilePic(doctorId);
        return ResponseEntity.ok()
                .contentType(imageDownloadDTO.getMediaType())
                .body(imageDownloadDTO.getResource());
    }

    //get list of unverified doctor
    @GetMapping("list-unverified-doctor")
    public List<DoctorDTO> getUnverifiedDoctors() {
        return doctorService.getUnverifiedDoctor();
    }

}

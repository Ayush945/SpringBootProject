package com.example.clinic_model.controller;

import com.example.clinic_model.dto.DoctorDTO;
import com.example.clinic_model.dto.LoginRequestDTO;
import com.example.clinic_model.dto.LoginResponseDTO;
import com.example.clinic_model.dto.PatientDTO;
import com.example.clinic_model.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class MockAuthentication {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("patient-register")
    public ResponseEntity<PatientDTO> registerAsStudent(@RequestBody PatientDTO patientDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.registerAsPatient(patientDTO));
    }

    @PostMapping("doctor-register")
    public ResponseEntity<DoctorDTO> registerAsDoctor(@RequestBody DoctorDTO doctorDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.registerAsDoctor(doctorDTO));
    }
    @PostMapping("patient-otp-verify")
    public ResponseEntity<String> verifyPatientOtp(@RequestBody int otp) {
        try {
            authenticationService.verifyOTP(otp);
            return ResponseEntity.status(HttpStatus.OK).body("OTP verification successful");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred during OTP verification");
        }
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginDTO) {
        return ResponseEntity.ok().body(authenticationService.login(loginDTO));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("doctor-verify/{doctorId}")
    public ResponseEntity<DoctorDTO> doctorVerify(@PathVariable Long doctorId) {
        return ResponseEntity.ok().body(authenticationService.verifyDoctor(doctorId));
    }
}

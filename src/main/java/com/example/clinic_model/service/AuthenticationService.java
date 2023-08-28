package com.example.clinic_model.service;

import com.example.clinic_model.dto.DoctorDTO;
import com.example.clinic_model.dto.LoginRequestDTO;
import com.example.clinic_model.dto.LoginResponseDTO;
import com.example.clinic_model.dto.PatientDTO;
import com.example.clinic_model.model.enums.RoleEnum;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;


public interface AuthenticationService {
    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);
    void createAdmin();
    PatientDTO registerAsPatient(PatientDTO patientDTO);
    DoctorDTO registerAsDoctor(DoctorDTO doctorDTO);
    void verifyOTP(int otp);
    DoctorDTO verifyDoctor(Long doctorId );

    UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(String username, RoleEnum role);
}

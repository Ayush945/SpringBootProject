package com.example.clinic_model.service;

import com.example.clinic_model.dto.ImageDTO;
import com.example.clinic_model.dto.PatientDTO;

import java.util.List;

public interface PatientService {
    PatientDTO createPatient(PatientDTO patientDTO);
    List<PatientDTO> getAllPatients();
    PatientDTO getPatientById(Long patientId);
    PatientDTO updatePatient(Long patientId, PatientDTO patientDTO);
    void deletePatientById(Long patientId);
    Integer countAllVerifiedPatient();
    List<PatientDTO> searchPatientStartingWith(String firstChar);
}

package com.example.clinic_model.service;

import com.example.clinic_model.dto.DoctorDTO;

import java.util.List;

public interface DoctorService {
    DoctorDTO createDoctor(DoctorDTO doctorDTO);
    DoctorDTO getDoctorById(Long id);
    List<DoctorDTO> getAllDoctors();
    DoctorDTO updateDoctor(Long doctorId, DoctorDTO doctorDTO);
    void deleteDoctorById(Long id);
}


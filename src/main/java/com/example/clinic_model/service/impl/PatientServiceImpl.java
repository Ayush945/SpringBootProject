package com.example.clinic_model.service.impl;

import com.example.clinic_model.dto.ImageDTO;
import com.example.clinic_model.dto.PatientDTO;
import com.example.clinic_model.exception.ResourceNotFoundException;
import com.example.clinic_model.model.Patient;
import com.example.clinic_model.repository.PatientRepository;
import com.example.clinic_model.service.PatientService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PatientDTO createPatient(PatientDTO patientDTO) {
        Patient patient = modelMapper.map(patientDTO, Patient.class);
        Patient patient1 = patientRepository.save(patient);
        return modelMapper.map(patient1, PatientDTO.class);
    }

    @Override
    public List<PatientDTO> getAllPatients() {
        List<Patient> patients = patientRepository.findAll();
        return patients.stream()
                .map(patient -> modelMapper.map(patient, PatientDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PatientDTO getPatientById(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
        return modelMapper.map(patient, PatientDTO.class);
    }

    @Override
    public PatientDTO updatePatient(Long patientId, PatientDTO patientDTO) {
        Optional<Patient> optionalPatient = patientRepository.findById(patientId);

        if (optionalPatient.isEmpty()) {
            throw new ResourceNotFoundException("Patient not found");
        }

        Patient existingPatient = optionalPatient.get();

        // Update properties from DTO
        if (patientDTO.getPatientName() != null) {
            existingPatient.setPatientName(patientDTO.getPatientName());
        }
        if (patientDTO.getAge() != 0) {
            existingPatient.setAge(patientDTO.getAge());
        }
        if (patientDTO.getAddress() != null) {
            existingPatient.setAddress(patientDTO.getAddress());
        }
        if (patientDTO.getEmail() != null) {
            existingPatient.setEmail(patientDTO.getEmail());
        }
        if (patientDTO.getPhone() != null) {
            existingPatient.setPhone(patientDTO.getPhone());
        }
        if (patientDTO.getOtp() != 0) {
            existingPatient.setOtp(patientDTO.getOtp());
        }
        if(patientDTO.getJoinDate()!=null){
            existingPatient.setJoinDate((patientDTO.getJoinDate()));
        }

        // Save the updated patient
        Patient updatedPatient = patientRepository.save(existingPatient);

        return modelMapper.map(updatedPatient, PatientDTO.class);
    }

    @Override
    public void deletePatientById(Long patientId) {
        if (!patientRepository.existsById(patientId)) {
            throw new ResourceNotFoundException("Patient not found");
        }
        patientRepository.deleteById(patientId);
    }

    @Override
    public List<PatientDTO> searchPatientStartingWith(String firstChar) {

        List<Patient> patients=patientRepository.findByPatientNameStartingWith(firstChar.toUpperCase());
        return patients.stream()
                .map(patient -> modelMapper.map(patient, PatientDTO.class))
                .collect(Collectors.toList());

    }
    @Override
    public Integer countAllVerifiedPatient() {
        return patientRepository.countAllByIsVerifiedTrue();
    }

}

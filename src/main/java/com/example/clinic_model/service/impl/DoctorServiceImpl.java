package com.example.clinic_model.service.impl;

import com.example.clinic_model.dto.DoctorDTO;
import com.example.clinic_model.exception.ResourceNotFoundException;
import com.example.clinic_model.model.Doctor;
import com.example.clinic_model.repository.DoctorRepository;
import com.example.clinic_model.service.DoctorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public DoctorDTO createDoctor(DoctorDTO doctorDTO) {
        Doctor doctor = modelMapper.map(doctorDTO, Doctor.class);
        doctor = doctorRepository.save(doctor);
        return modelMapper.map(doctor, DoctorDTO.class);
    }

    @Override
    public List<DoctorDTO> getAllDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();
        return doctors.stream()
                .map(doctor -> modelMapper.map(doctor, DoctorDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public DoctorDTO getDoctorById(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));
        return modelMapper.map(doctor, DoctorDTO.class);
    }

    @Override
    public DoctorDTO updateDoctor(Long doctorId, DoctorDTO doctorDTO) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(doctorId);

        if (optionalDoctor.isEmpty()) {
            throw new ResourceNotFoundException("Doctor not found");
        }

        Doctor existingDoctor = optionalDoctor.get();

        // Update properties from DTO
        if (doctorDTO.getDoctorName() != null) {
            existingDoctor.setDoctorName(doctorDTO.getDoctorName());
        }
        if (doctorDTO.getSpecialization() != null) {
            existingDoctor.setSpecialization(doctorDTO.getSpecialization());
        }
        if (doctorDTO.getEmail() != null) {
            existingDoctor.setEmail(doctorDTO.getEmail());
        }
        if (doctorDTO.getPhone() != null) {
            existingDoctor.setPhone(doctorDTO.getPhone());
        }
        if (doctorDTO.getJoinDate() != null) {
            existingDoctor.setJoinDate(doctorDTO.getJoinDate());
        }
        if (doctorDTO.getDoctorLicense() != null) {
            existingDoctor.setDoctorLicense(doctorDTO.getDoctorLicense());
        }
        // Save the updated doctor
        Doctor updatedDoctor = doctorRepository.save(existingDoctor);

        return modelMapper.map(updatedDoctor, DoctorDTO.class);
    }
    @Override
    public void deleteDoctorById(Long doctorId) {
        if (!doctorRepository.existsById(doctorId)) {
            throw new ResourceNotFoundException("Doctor not found");
        }
        doctorRepository.deleteById(doctorId);
    }

    @Override
    public List<DoctorDTO> getUnverifiedDoctor() {
        List<Doctor> doctors = doctorRepository.findAll();

        return doctors.stream()
                .filter(doctor -> !doctor.isVerified()) // Filter out the verified doctors
                .map(doctor -> modelMapper.map(doctor, DoctorDTO.class))
                .collect(Collectors.toList());
    }
}

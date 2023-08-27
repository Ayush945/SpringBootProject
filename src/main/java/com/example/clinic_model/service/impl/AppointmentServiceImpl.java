package com.example.clinic_model.service.impl;

import com.example.clinic_model.dto.AppointmentDTO;
import com.example.clinic_model.dto.DoctorDTO;
import com.example.clinic_model.dto.PatientDTO;
import com.example.clinic_model.exception.ResourceNotFoundException;
import com.example.clinic_model.model.Appointment;
import com.example.clinic_model.model.Doctor;
import com.example.clinic_model.model.Patient;
import com.example.clinic_model.repository.AppointmentRepository;
import com.example.clinic_model.service.AppointmentService;
import com.example.clinic_model.service.DoctorService;
import com.example.clinic_model.service.PatientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

//    @Override
//    public AppointmentDTO createAppointment(AppointmentDTO appointmentDTO) {
//        Appointment appointment = modelMapper.map(appointmentDTO, Appointment.class);
//        appointment = appointmentRepository.save(appointment);
//        return modelMapper.map(appointment, AppointmentDTO.class);
//    }


    @Override
    public AppointmentDTO createAppointment(AppointmentDTO appointmentDTO, Long patientId, Long doctorId) {
        Appointment appointment = modelMapper.map(appointmentDTO, Appointment.class);

        DoctorDTO doctorDTO = this.doctorService.getDoctorById(doctorId);
        Doctor doctor = modelMapper.map(doctorDTO, Doctor.class);

        PatientDTO patientDTO = this.patientService.getPatientById(patientId);
        Patient patient = modelMapper.map(patientDTO, Patient.class);


        appointment.setPatient(patient);
        appointment.setDoctor(doctor);


        Appointment savedAppointment = this.appointmentRepository.save(appointment);
        return modelMapper.map(savedAppointment, AppointmentDTO.class);
    }



    @Override
    public List<AppointmentDTO> getAllAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();
        return appointments.stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public AppointmentDTO getAppointmentById(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));
        return modelMapper.map(appointment, AppointmentDTO.class);
    }

    @Override
    public AppointmentDTO updateAppointment(Long appointmentId, AppointmentDTO appointmentDTO) {
        Optional<Appointment> savedAppointment = appointmentRepository.findById(appointmentId);

        if (savedAppointment.isEmpty()) {
            throw new ResourceNotFoundException("Appointment not found");
        }

        Appointment existingAppointment = savedAppointment.get();

        // Update properties from DTO
        if (appointmentDTO.getAppointmentDate() != null) {
            existingAppointment.setAppointmentDate(appointmentDTO.getAppointmentDate());
        }
        if (appointmentDTO.getAppointmentTime() != null) {
            existingAppointment.setAppointmentTime(appointmentDTO.getAppointmentTime());
        }
        if (appointmentDTO.getAppointmentDescription() != null) {
            existingAppointment.setAppointmentDescription(appointmentDTO.getAppointmentDescription());
        }
        if (appointmentDTO.getAppointmentStatus() != null) {
            existingAppointment.setAppointmentStatus(appointmentDTO.getAppointmentStatus());
        }
        if(appointmentDTO.getFollowUpDateAndTime()!=null){
            existingAppointment.setFollowUpDateAndTime(appointmentDTO.getFollowUpDateAndTime());
        }
        Appointment updatedAppointment = appointmentRepository.save(existingAppointment);

        return modelMapper.map(updatedAppointment, AppointmentDTO.class);
    }


    @Override
    public void deleteAppointmentById(Long appointmentId) {
        if (!appointmentRepository.existsById(appointmentId)) {
            throw new ResourceNotFoundException("Appointment not found");
        }
        appointmentRepository.deleteById(appointmentId);
    }

    @Override
    public List<AppointmentDTO> findAppointment(Long patientId, Long doctorId) {
        List<Appointment> appointments = this.appointmentRepository.findByPatientPatientIdAndDoctorDoctorId(patientId,
                doctorId);


        return appointments.stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentDTO.class))
                .collect(Collectors.toList());
    }
}

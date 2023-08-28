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

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
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

    @Override
    public AppointmentDTO createAppointment(AppointmentDTO appointmentDTO, Long patientId, Long doctorId) {
        Appointment appointment = modelMapper.map(appointmentDTO, Appointment.class);

        DoctorDTO doctorDTO = this.doctorService.getDoctorById(doctorId);
        Doctor doctor = modelMapper.map(doctorDTO, Doctor.class);

        PatientDTO patientDTO = this.patientService.getPatientById(patientId);
        Patient patient = modelMapper.map(patientDTO, Patient.class);

        try {
            appointment.setPatient(patient);
            appointment.setDoctor(doctor);

            Appointment savedAppointment = this.appointmentRepository.save(appointment);
            return modelMapper.map(savedAppointment, AppointmentDTO.class);
        } catch (Exception e) {
            // Handle any exceptions that may occur during saving the appointment
            // For example:
            throw new IllegalArgumentException("Failed to create appointment");
        }
    }



    @Override
    public List<AppointmentDTO> getAllAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();
        return appointments.stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentDTO> getAppointmentByPatientId(Long patientId) {
        List<Appointment> appointments = appointmentRepository.findByPatientPatientId(patientId);
        return appointments.stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentDTO> getAppointmentByDoctorId(Long doctorId) {
        List<Appointment> appointments = appointmentRepository.findByDoctorDoctorId(doctorId);
        return appointments.stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentDTO.class))
                .collect(Collectors.toList());
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

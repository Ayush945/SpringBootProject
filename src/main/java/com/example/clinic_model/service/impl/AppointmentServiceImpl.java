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
import java.util.ArrayList;
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
        LocalDate currentDate = LocalDate.now();
        List<Appointment> appointments = this.appointmentRepository
                .findAllByAppointmentDateAfterOrAppointmentDateEqualsAndDoctorDoctorId(currentDate, currentDate, doctorId);

        List<AppointmentDTO> allDoctorAppointment = new ArrayList<>();
        for (Appointment appointment : appointments) {
            allDoctorAppointment.add(modelMapper.map(appointment, AppointmentDTO.class));
        }

        return allDoctorAppointment;
    }

    public List<AppointmentDTO> getAppointmentHistory(Long doctorId) {
        LocalDate currentDate = LocalDate.now();
        List<Appointment> appointments = this.appointmentRepository
                .findAllByAppointmentDateBeforeAndDoctorDoctorId(currentDate, doctorId);

        List<AppointmentDTO> allAppointmentHistory = new ArrayList<>();
        for (Appointment appointment : appointments) {
            allAppointmentHistory.add(modelMapper.map(appointment, AppointmentDTO.class));
        }

        return allAppointmentHistory;
    }

    @Override
    public AppointmentDTO getAppointmentById(Long appointmentId) {
        Appointment appointment=appointmentRepository.findById(appointmentId)
                .orElseThrow(()->new RuntimeException("Not found appointment"));
        return modelMapper.map(appointment,AppointmentDTO.class);
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

    @Override
    public Integer countDistinctPatientIdsByDoctorId(Long doctorId) {
        return appointmentRepository.countDistinctPatientIdsByDoctorId(doctorId);
    }

    @Override
    public Integer countAppointmentsForToday() {
        return appointmentRepository.countByAppointmentDate(LocalDate.now());
    }






}

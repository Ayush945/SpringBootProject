package com.example.clinic_model.service.impl;

import com.example.clinic_model.dto.AppointmentDTO;
import com.example.clinic_model.model.Appointment;
import com.example.clinic_model.repository.AppointmentRepository;
import com.example.clinic_model.repository.DoctorRepository;
import com.example.clinic_model.repository.PatientRepository;
import com.example.clinic_model.service.EndPointService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EndPointServiceImpl implements EndPointService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public Integer countAllPatient() {
        return patientRepository.countAllPatient();
    }

    @Override
    public Integer countAllDoctor() {
        return doctorRepository.countAllDoctor();
    }

    @Override
    public Integer countTodayAppointment() {
        return appointmentRepository.countTodayAppointment();
    }

    @Override
    public Integer countWeeklyAppointment() {
        return appointmentRepository.countWeeklyAppointment();
    }

    //    @Override
//    public List<AppointmentDTO> getTodayAppointmentList(Long doctorId) {
//        List<Appointment>appointment2= appointmentRepository.getTodayAppointmentList(doctorId);
//        return appointment2.stream()
//                .map(appointment -> modelMapper.map(appointment,AppointmentDTO.class))
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<AppointmentDTO> getHistoryAppointmentList(Long doctorId) {
//        List<Appointment> appointment1= appointmentRepository.getHistoryAppointmentList(doctorId);
//        return appointment1.stream()
//                .map(appointment -> modelMapper.map(appointment1,AppointmentDTO.class))
//                .collect(Collectors.toList());
//    }
//    //patient
//    @Override
//    public List<AppointmentDTO> getPatientAppointmentList(Long patientId) {
//        List<Appointment> appointment3= appointmentRepository.getPatientAppointmentList(patientId);
//        return appointment3.stream()
//                .map(appointment -> modelMapper.map(appointment3,AppointmentDTO.class))
//                .collect(Collectors.toList());
//    }
    @Override
    public List<Appointment> getTodayAppointmentList(Long doctorId) {
        List<Appointment>appointment2= appointmentRepository.getTodayAppointmentList(doctorId);
        return appointment2;
    }

    @Override
    public List<Appointment> getHistoryAppointmentList(Long doctorId) {
        List<Appointment> appointment1= appointmentRepository.getHistoryAppointmentList(doctorId);
        return appointment1;
    }
    //patient
    @Override
    public List<Appointment> getPatientAppointmentList(Long patientId) {
        List<Appointment> appointment3= appointmentRepository.getPatientAppointmentList(patientId);
        return appointment3;
    }
}

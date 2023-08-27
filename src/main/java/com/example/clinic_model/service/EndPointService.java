package com.example.clinic_model.service;

import com.example.clinic_model.dto.AppointmentDTO;
import com.example.clinic_model.model.Appointment;

import java.util.List;

public interface EndPointService {
    Integer countAllPatient();

    Integer countAllDoctor();

    Integer countTodayAppointment();

    Integer countWeeklyAppointment();

    List<Appointment> getTodayAppointmentList(Long doctorId);

    List<Appointment> getHistoryAppointmentList(Long doctorId);

    List<Appointment> getPatientAppointmentList(Long patientId);
}
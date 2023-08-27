package com.example.clinic_model.service;

import com.example.clinic_model.dto.AppointmentDTO;

import java.util.List;

public interface AppointmentService {
    AppointmentDTO createAppointment(AppointmentDTO appointmentDTO, Long patientId, Long doctorId);
    List<AppointmentDTO> getAllAppointments();
    AppointmentDTO getAppointmentById(Long appointmentId);
    AppointmentDTO updateAppointment(Long appointmentId, AppointmentDTO appointmentDTO);
    void deleteAppointmentById(Long appointmentId);

    List<AppointmentDTO> findAppointment(Long patientId, Long doctorId);
}

package com.example.clinic_model.service;

import com.example.clinic_model.dto.AppointmentDTO;

import java.util.List;

public interface AppointmentService {
    AppointmentDTO createAppointment(AppointmentDTO appointmentDTO, Long patientId, Long doctorId);
    List<AppointmentDTO> getAllAppointments();
    List<AppointmentDTO> getAppointmentByPatientId(Long appointmentId);
    void deleteAppointmentById(Long appointmentId);

    List<AppointmentDTO> findAppointment(Long patientId, Long doctorId);

    List<AppointmentDTO> getAppointmentByDoctorId(Long doctorId);

    List<AppointmentDTO> getAppointmentHistory(Long doctorId);

    AppointmentDTO getAppointmentById(Long appointmentId);

    Integer countDistinctPatientIdsByDoctorId(Long doctorId);
}

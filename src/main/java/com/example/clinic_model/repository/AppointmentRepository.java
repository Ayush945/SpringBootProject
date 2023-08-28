package com.example.clinic_model.repository;

import com.example.clinic_model.dto.AppointmentDTO;
import com.example.clinic_model.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment,Long> {

    List<Appointment>findByPatientPatientId(Long patientId);

    List<Appointment> findByPatientPatientIdAndDoctorDoctorId(Long patientId, Long doctorId);

    List<Appointment> findByDoctorDoctorId(Long doctorId);
}
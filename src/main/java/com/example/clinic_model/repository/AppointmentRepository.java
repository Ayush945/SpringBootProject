package com.example.clinic_model.repository;

import com.example.clinic_model.dto.AppointmentDTO;
import com.example.clinic_model.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
    @Query("SELECT a FROM Appointment a WHERE a.doctor.doctorId = :doctorId AND a.appointmentDate<CURRENT_DATE")
    List<Appointment> getHistoryAppointmentList(@Param("doctorId") Long doctorId);

    @Query("SELECT a FROM Appointment a WHERE a.doctor.doctorId = :doctorId AND a.appointmentDate=CURRENT_DATE ")
    List<Appointment> getTodayAppointmentList(@Param("doctorId") Long doctorId);

    @Query("SELECT a FROM Appointment a WHERE a.patient.patientId = :patientId")
    List<Appointment> getPatientAppointmentList(@Param("patientId") Long patientId);
    @Query("SELECT COUNT(*) FROM Appointment a WHERE a.appointmentDate=CURRENT_DATE ")
    Integer countWeeklyAppointment();
    @Query("SELECT COUNT(*) FROM Appointment a WHERE a.appointmentDate=CURRENT_DATE ")
    Integer countTodayAppointment();

    //
    List<Appointment> findByPatientPatientIdAndDoctorDoctorId(Long patientId, Long doctorId);

    //To get appointment of patient
    List<Appointment> findByPatientPatientId(Long patientId);

}
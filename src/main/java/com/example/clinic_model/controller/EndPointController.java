package com.example.clinic_model.controller;

import com.example.clinic_model.model.Appointment;
import com.example.clinic_model.service.EndPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EndPointController {
    @Autowired
    private EndPointService endPointService;
    @GetMapping("/count-patient")
    public Integer countAllPatient(){
        return endPointService.countAllPatient();
    }
    @GetMapping("/count-doctor")
    public Integer countAllDoctor(){
        return endPointService.countAllDoctor();
    }

    @GetMapping("/count-today-appointment")
    public Integer countTodayAppointment(){
        return endPointService.countTodayAppointment();
    }

    @GetMapping("/count-weekly-appointment")
    public Integer countWeeklyAppointment(){
        return endPointService.countWeeklyAppointment();
    }

    //GET Doctor daily appointment-not working
//    @GetMapping("/get-today-appointmentList/{doctorId}")
//    public List<AppointmentDTO> getTodayAppointmentList(@PathVariable("doctorId") Long doctorId){
//        return endPointService.getTodayAppointmentList(doctorId);
//    }
//
//    //Get patients appointment
//    @GetMapping("/get-all-appointmentList/{patientId}")
//    public List<AppointmentDTO> getPatientAppointmentList(@PathVariable("patientId") Long patientId){
//        return endPointService.getPatientAppointmentList(patientId);
//    }
//    //Get doctor previous appointment-works fine
//    @GetMapping("/get-history-appointmentList/{doctorId}")
//    public List<AppointmentDTO> getHistoryAppointmentList(@PathVariable("doctorId") Long doctorId){
//        return endPointService.getHistoryAppointmentList(doctorId);
//    }

    //GET Doctor daily appointment-not working
    @GetMapping("/get-today-appointmentList/{doctorId}")
    public List<Appointment> getTodayAppointmentList(@PathVariable("doctorId") Long doctorId){
        return endPointService.getTodayAppointmentList(doctorId);
    }

    //Get patients appointment
    @GetMapping("/get-all-appointmentList/{patientId}")
    public List<Appointment> getPatientAppointmentList(@PathVariable("patientId") Long patientId){
        return endPointService.getPatientAppointmentList(patientId);
    }
    //Get doctor previous appointment-works fine
    @GetMapping("/get-history-appointmentList/{doctorId}")
    public List<Appointment> getHistoryAppointmentList(@PathVariable("doctorId") Long doctorId){
        return endPointService.getHistoryAppointmentList(doctorId);
    }

}

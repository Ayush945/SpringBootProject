package com.example.clinic_model.controller;

import com.example.clinic_model.dto.AppointmentDTO;
import com.example.clinic_model.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }
    //create appointment using both doctor and patient

    @PreAuthorize("hasAuthority('ROLE_PATIENT')")
    @PostMapping("/patient/{patientId}/doctor/{doctorId}")
    public ResponseEntity<AppointmentDTO> createAppointment(@Valid @RequestBody AppointmentDTO appointmentDTO,
                                                            @PathVariable("patientId") Long patientId,
                                                            @PathVariable("doctorId") Long doctorId){
        AppointmentDTO createdAppointment = appointmentService.createAppointment(appointmentDTO, patientId, doctorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAppointment);
    }

    //for admin to look at list of appointment
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/get-all-appointments")
    public List<AppointmentDTO> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    //for patient to look at his list of appointment
//    @PreAuthorize("hasAuthority('ROLE_PATIENT')")
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<AppointmentDTO>>getAppointmentByPatientId(@PathVariable Long patientId) {
        List<AppointmentDTO> appointmentDTO = appointmentService.getAppointmentByPatientId(patientId);
        return ResponseEntity.ok(appointmentDTO);
    }
    @PreAuthorize("hasAuthority('ROLE_DOCTOR')")
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<AppointmentDTO>>getAppointmentByDoctorId(@PathVariable Long doctorId) {
        List<AppointmentDTO> appointmentDTO = appointmentService.getAppointmentByDoctorId(doctorId);
        return ResponseEntity.ok(appointmentDTO);
    }

    //to delete appointment
    @PreAuthorize("hasAuthority('ROLE_PATIENT")
    @DeleteMapping("/{patientId}/{appointmentId}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointmentById(id);
        return ResponseEntity.noContent().build();
    }

    // getting appointment history
    @PreAuthorize("hasAuthority('ROLE_DOCTOR')")
    @GetMapping("get-appointment-history/doctor/{doctorId}")
    public ResponseEntity<List<AppointmentDTO>> getPatientsWithPastAppointmentsForDoctor(@PathVariable Long doctorId) {
        List<AppointmentDTO> appointmentHistory = this.appointmentService.getAppointmentHistory(doctorId);
        return ResponseEntity.ok(appointmentHistory);
    }

    @GetMapping("get-appointment/{appointmentId}")
    public ResponseEntity<AppointmentDTO> getAppointmentByAppointmentId(@PathVariable Long appointmentId){
        AppointmentDTO appointmentDTO=this.appointmentService.getAppointmentById(appointmentId);
                return ResponseEntity.ok(appointmentDTO);
    }

    @GetMapping("/count/treated-patient/{doctorId}")
    public ResponseEntity<Integer> countDistinctPatientIdsByDoctorId(@PathVariable Long doctorId) {
        Integer count = appointmentService.countDistinctPatientIdsByDoctorId(doctorId);
        return ResponseEntity.ok(count);
    }


}

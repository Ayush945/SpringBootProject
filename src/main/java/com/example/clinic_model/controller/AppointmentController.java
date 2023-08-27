package com.example.clinic_model.controller;

import com.example.clinic_model.dto.AppointmentDTO;
import com.example.clinic_model.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/patient/{patientId}/doctor/{doctorId}")
    public ResponseEntity<AppointmentDTO> createAppointment(@Valid @RequestBody AppointmentDTO appointmentDTO,
                                                            @PathVariable("patientId") Long patientId,
                                                            @PathVariable("doctorId") Long doctorId){
        AppointmentDTO createdAppointment = appointmentService.createAppointment(appointmentDTO, patientId, doctorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAppointment);
    }

    @GetMapping
    public List<AppointmentDTO> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> getAppointmentById(@PathVariable Long id) {
        AppointmentDTO appointmentDTO = appointmentService.getAppointmentById(id);
        return ResponseEntity.ok(appointmentDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDTO> updateAppointment(@PathVariable Long id,
                                                            @RequestBody AppointmentDTO appointmentDTO) {
        AppointmentDTO updatedAppointment = appointmentService.updateAppointment(id, appointmentDTO);
        return ResponseEntity.ok(updatedAppointment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointmentById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/{patientId}/{doctorId}")
    public ResponseEntity<List<AppointmentDTO>> getByPatientIdAndDoctorId(@PathVariable("patientId") Long patientId,
                                                                          @PathVariable("doctorId") Long doctorId)
    {
        return ResponseEntity.ok(this.appointmentService.findAppointment(patientId, doctorId));

    }
}

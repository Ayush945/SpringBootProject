package com.example.clinic_model.controller;

import com.example.clinic_model.dto.AdminDTO;
import com.example.clinic_model.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    public ResponseEntity<AdminDTO> createAdmin(@RequestBody AdminDTO adminDTO) {
        AdminDTO createdAdmin = this.adminService.createAdmin(adminDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAdmin);
    }

    @GetMapping
    public List<AdminDTO> getAllAdmin() {
        return this.adminService.getAllAdmins();
    }
    @GetMapping("/{id}")
    public ResponseEntity<AdminDTO> getAdminById(@PathVariable("id") Long adminId) {
        AdminDTO adminDTO = this.adminService.getAdminById(adminId);
        return ResponseEntity.ok(adminDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdminDTO> updateAdminById(@PathVariable("id") Long adminId,
                                                    @RequestBody AdminDTO adminDTO) {
        AdminDTO updatedAdmin = this.adminService.updateAdmin(adminId, adminDTO);
        return ResponseEntity.ok(updatedAdmin);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdminById(@PathVariable("id") Long adminId) {
        this.adminService.deleteAdminById(adminId);
        return ResponseEntity.noContent().build();
    }
}

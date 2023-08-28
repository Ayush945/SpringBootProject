package com.example.clinic_model.controller;

import com.example.clinic_model.dto.AdminDTO;
import com.example.clinic_model.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<AdminDTO> updateAdminById(@PathVariable("id") Long adminId,
                                                    @RequestBody AdminDTO adminDTO) {
        AdminDTO updatedAdmin = this.adminService.updateAdmin(adminId, adminDTO);
        return ResponseEntity.ok(updatedAdmin);
    }
}

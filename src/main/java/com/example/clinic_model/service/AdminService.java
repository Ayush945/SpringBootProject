package com.example.clinic_model.service;

import com.example.clinic_model.dto.AdminDTO;

import java.util.List;

public interface AdminService {
    AdminDTO createAdmin(AdminDTO adminDTO);

    List<AdminDTO> getAllAdmins();

    AdminDTO getAdminById(Long adminId);

    AdminDTO updateAdmin(Long adminId, AdminDTO adminDTO);

    void deleteAdminById(Long adminId);
}

package com.example.clinic_model.service.impl;

import com.example.clinic_model.dto.AdminDTO;
import com.example.clinic_model.exception.ResourceNotFoundException;
import com.example.clinic_model.model.Admin;
import com.example.clinic_model.repository.AdminRepository;
import com.example.clinic_model.service.AdminService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AdminDTO createAdmin(AdminDTO adminDTO) {
        Admin admin = modelMapper.map(adminDTO, Admin.class);
        admin = adminRepository.save(admin);
        return modelMapper.map(admin, AdminDTO.class);
    }

    @Override
    public List<AdminDTO> getAllAdmins() {
        List<Admin> admins = adminRepository.findAll();
        return admins.stream()
                .map(admin -> modelMapper.map(admin, AdminDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public AdminDTO getAdminById(Long adminId) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found!"));
        return modelMapper.map(admin, AdminDTO.class);
    }

    @Override
    public AdminDTO updateAdmin(Long adminId, AdminDTO adminDTO) {
        Optional<Admin> savedAdmin = adminRepository.findById(adminId);

        if (savedAdmin.isEmpty()) {
            throw new ResourceNotFoundException("Admin not found");
        }

        Admin existingAdmin = savedAdmin.get();

        // Update properties from DTO
        if (adminDTO.getAdminName() != null) {
            existingAdmin.setAdminName(adminDTO.getAdminName());
        }
        if (adminDTO.getPhone() != null) {
            existingAdmin.setPhone(adminDTO.getPhone());
        }
        if (adminDTO.getEmail() != null) {
            existingAdmin.setEmail(adminDTO.getEmail());
        }
        if(adminDTO.getJoinDate()!=null){
            existingAdmin.setJoinDate((adminDTO.getJoinDate()));
        }


        Admin updatedAdmin = adminRepository.save(existingAdmin);

        return modelMapper.map(updatedAdmin, AdminDTO.class);
    }

    @Override
    public void deleteAdminById(Long adminId) {
        if (!adminRepository.existsById(adminId))
            throw new ResourceNotFoundException("Admin not found!");

        adminRepository.deleteById(adminId);
    }
}

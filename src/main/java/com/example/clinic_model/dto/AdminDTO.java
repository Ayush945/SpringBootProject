package com.example.clinic_model.dto;

import com.example.clinic_model.model.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AdminDTO extends UserDTO {
    private Long adminId;

    private String adminName;
    private final RoleEnum role = RoleEnum.ROLE_ADMIN;
}

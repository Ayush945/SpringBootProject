package com.example.clinic_model.dto;

import com.example.clinic_model.model.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginRequestDTO {

    @NotBlank(message = "Username cannot be blank")
    @Size(max = 100, message = "Username must be less than 100 characters")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 4, max = 15, message = "Password must be between 4 and 15 characters")
    private String password;

    @NotNull(message = "Role is required")
    private RoleEnum role;
}

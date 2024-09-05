package com.example.companymanager.api.company;

import com.example.companymanager.api.departament.DepartmentCreateDto;
import jakarta.validation.constraints.NotBlank;

import java.util.List;


public record CompanyCreateDto(@NotBlank String name, List<DepartmentCreateDto> departments) {
}

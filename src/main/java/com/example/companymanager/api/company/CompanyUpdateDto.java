package com.example.companymanager.api.company;

import com.example.companymanager.api.departament.DepartmentDto;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record CompanyUpdateDto(@NotBlank String name, List<DepartmentDto> departments) {
}

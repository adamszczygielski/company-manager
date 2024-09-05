package com.example.companymanager.api.company;

import com.example.companymanager.api.departament.DepartmentDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;


public record CompanyDto(@NotNull Long id, @NotBlank String name, List<DepartmentDto> departments) {
}

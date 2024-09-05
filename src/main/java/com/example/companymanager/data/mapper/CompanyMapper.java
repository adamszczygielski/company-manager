package com.example.companymanager.data.mapper;

import com.example.companymanager.api.company.CompanyDto;
import com.example.companymanager.api.company.CompanyCreateDto;
import com.example.companymanager.api.company.CompanyUpdateDto;
import com.example.companymanager.data.enitity.Company;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    CompanyDto toCompanyDto(Company company);

    Company toCompany(CompanyCreateDto companyCreateDto);

    Company toCompany(Long id, CompanyUpdateDto companyUpdateDto);
}

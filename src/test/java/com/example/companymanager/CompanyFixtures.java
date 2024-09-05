package com.example.companymanager;

import com.example.companymanager.api.company.CompanyCreateDto;
import com.example.companymanager.api.departament.DepartmentCreateDto;
import com.example.companymanager.api.manager.ManagerCreateDto;
import com.example.companymanager.api.project.ProjectCreateDto;
import com.example.companymanager.api.team.TeamCreateDto;

import java.util.List;

public class CompanyFixtures {

    public static CompanyCreateDto companyCreateDto() {
        ManagerCreateDto managerCreateDto = new ManagerCreateDto("Manager contact info");
        ProjectCreateDto projectCreateDto = new ProjectCreateDto("Project name", managerCreateDto);
        TeamCreateDto teamCreateDto = new TeamCreateDto("Team name", projectCreateDto);
        DepartmentCreateDto departmentCreateDto = new DepartmentCreateDto("Department name", List.of(teamCreateDto));
        return new CompanyCreateDto("Company name", List.of(departmentCreateDto));
    }
}

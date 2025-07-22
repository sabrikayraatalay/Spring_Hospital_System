package com.KayraAtalay.Hospital_System.controller;

import java.util.List;

import com.KayraAtalay.Hospital_System.dto.DepartmentDto;
import com.KayraAtalay.Hospital_System.dto.DepartmentDtoIU;
import com.KayraAtalay.Hospital_System.dto.DoctorDto;
import com.KayraAtalay.Hospital_System.entities.RootEntity;

public interface IDepartmentController {
	
	public RootEntity<DepartmentDto> saveDepartment(DepartmentDtoIU departmentDtoIU);

	public RootEntity<String> deleteDepartment(Long id);

	public RootEntity<List<DoctorDto>> departmentList(Long id);
	
	public RootEntity<List<DepartmentDto>> getAllDepartments();

}

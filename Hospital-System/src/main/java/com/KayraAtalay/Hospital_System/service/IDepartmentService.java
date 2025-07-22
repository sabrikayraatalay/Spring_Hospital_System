package com.KayraAtalay.Hospital_System.service;

import java.util.List;

import com.KayraAtalay.Hospital_System.dto.DepartmentDto;
import com.KayraAtalay.Hospital_System.dto.DepartmentDtoIU;
import com.KayraAtalay.Hospital_System.dto.DoctorDto;

public interface IDepartmentService {
	
	public DepartmentDto saveDepartment(DepartmentDtoIU departmentDtoIU);
	
	public void deleteDepartment(Long id);
	
	public List<DoctorDto> departmentList(Long id);
	
	public List<DepartmentDto> getAllDepartments();
	
	
}

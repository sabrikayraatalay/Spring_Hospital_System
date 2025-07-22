package com.KayraAtalay.Hospital_System.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDto {
	
	private Long id;
	
	private String name;
	
	private List<DoctorDto> department_doctors = new ArrayList<>();

}

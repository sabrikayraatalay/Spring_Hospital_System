package com.KayraAtalay.Hospital_System.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDto {
	
	private Long id;
	
	private String firstName;
	
    private String lastName;
    
    private String specialization;
    
    private String departmentName;

}

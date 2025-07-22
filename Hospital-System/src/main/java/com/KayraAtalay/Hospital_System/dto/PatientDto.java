package com.KayraAtalay.Hospital_System.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDto {
	
	private Long id;

	private String firstName;

	private String lastName;

	private String phone;

	private String email;

}

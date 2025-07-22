package com.KayraAtalay.Hospital_System.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDtoIU {

	@NotEmpty(message = "First name cannot be empty")
	private String firstName;

	@NotEmpty(message = "Last name cannot be empty")
	private String lastName;

	@NotEmpty(message = "Specialization cannot be empty")
	private String specialization;

	@NotNull(message = "Department id is required")
	private Long departmentId;
}

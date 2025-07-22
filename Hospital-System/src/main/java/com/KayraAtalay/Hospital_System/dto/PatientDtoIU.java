package com.KayraAtalay.Hospital_System.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDtoIU {

	@NotEmpty(message = "First Name Can Not Be Empty")
	private String firstName;

	@NotEmpty(message = "Last Name Can Not Be Empty")
	private String lastName;

	@NotEmpty(message = "Tc Number Can Not Be Empty")
	@Pattern(regexp = "\\d{11}", message = "TC Number Must Contain 11 Numbers")
	private String tcNo;

	@NotEmpty(message = "Phone Number Can Not Be Empty")
	@Pattern(regexp = "\\d{11}", message = "Phone Number Must Contain 11 Numbers")
	private String phone;

	@NotEmpty(message = "E-mail Can Not Be Empty")
	@Email
	private String email;

}

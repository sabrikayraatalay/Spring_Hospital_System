package com.KayraAtalay.Hospital_System.dto;

import java.time.LocalDateTime;

import com.KayraAtalay.Hospital_System.entities.AppointmentStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDto {

	private Long id;

	private String patientFullName;

	private String doctorFullName;

	private String doctorSpecialization;

	private String departmentName;

	private LocalDateTime appointmentTime;

	private AppointmentStatus status;
}

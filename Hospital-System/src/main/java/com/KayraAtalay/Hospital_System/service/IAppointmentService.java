package com.KayraAtalay.Hospital_System.service;

import java.time.LocalDateTime;
import java.util.List;

import com.KayraAtalay.Hospital_System.dto.AppointmentDto;
import com.KayraAtalay.Hospital_System.dto.AppointmentDtoIU;

public interface IAppointmentService {

	public AppointmentDto createAppointment(AppointmentDtoIU appointmentDtoIU);

	public AppointmentDto updateAppointment(Long id, LocalDateTime dateTime);
	
	public AppointmentDto completeAppointment(Long id);

	public AppointmentDto cancelAppointment(Long id);

	public AppointmentDto getAppointmentById(Long id);

	public List<AppointmentDto> getAllAppointments();

	public List<AppointmentDto> getAppointmentsByPatientId(Long patientId);

	public List<AppointmentDto> getAppointmentsByDoctorId(Long doctorId);

}

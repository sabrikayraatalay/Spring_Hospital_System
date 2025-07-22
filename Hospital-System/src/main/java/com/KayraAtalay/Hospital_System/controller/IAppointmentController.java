package com.KayraAtalay.Hospital_System.controller;

import java.time.LocalDateTime;
import java.util.List;

import com.KayraAtalay.Hospital_System.dto.AppointmentDto;
import com.KayraAtalay.Hospital_System.dto.AppointmentDtoIU;
import com.KayraAtalay.Hospital_System.entities.RootEntity;

public interface IAppointmentController {

	public RootEntity<AppointmentDto> createAppointment(AppointmentDtoIU appointmentDtoIU);

	public RootEntity<AppointmentDto> updateAppointment(Long id, LocalDateTime dateTime);

	public RootEntity<AppointmentDto> completeAppointment(Long id);

	public RootEntity<AppointmentDto> cancelAppointment(Long id);

	public RootEntity<AppointmentDto> getAppointmentById(Long id);

	public RootEntity<List<AppointmentDto>> getAllAppointments();

	public RootEntity<List<AppointmentDto>> getAppointmentsByPatientId(Long patientId);

	public RootEntity<List<AppointmentDto>> getAppointmentsByDoctorId(Long doctorId);

}

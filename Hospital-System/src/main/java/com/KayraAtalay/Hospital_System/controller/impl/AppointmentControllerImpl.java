package com.KayraAtalay.Hospital_System.controller.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.KayraAtalay.Hospital_System.controller.IAppointmentController;
import com.KayraAtalay.Hospital_System.dto.AppointmentDto;
import com.KayraAtalay.Hospital_System.dto.AppointmentDtoIU;
import com.KayraAtalay.Hospital_System.entities.RootEntity;
import com.KayraAtalay.Hospital_System.service.IAppointmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/rest/api/appointment")
public class AppointmentControllerImpl extends BaseController implements IAppointmentController {

	@Autowired
	private IAppointmentService appointmentService;

	@Override
	@PostMapping("/create")
	public RootEntity<AppointmentDto> createAppointment(@Valid @RequestBody AppointmentDtoIU appointmentDtoIU) {
		return ok(appointmentService.createAppointment(appointmentDtoIU));
	}

	@Override
	@PutMapping("/update/{id}")
	public RootEntity<AppointmentDto> updateAppointment(@Valid @PathVariable Long id, @RequestBody LocalDateTime dateTime) {
		return ok(appointmentService.updateAppointment(id, dateTime));
	}

	@Override
	@PutMapping("/complete/{id}")
	public RootEntity<AppointmentDto> completeAppointment(@PathVariable Long id) {
		return  ok(appointmentService.completeAppointment(id));
	}

	@Override
	@PutMapping("/cancel/{id}")
	public RootEntity<AppointmentDto> cancelAppointment(@PathVariable Long id) {
	return ok(appointmentService.cancelAppointment(id));
	}

	@Override
	@GetMapping("/{id}")
	public RootEntity<AppointmentDto> getAppointmentById(@PathVariable Long id) {
		return ok(appointmentService.getAppointmentById(id));
	}

	@Override
	@GetMapping("/list")
	public RootEntity<List<AppointmentDto>> getAllAppointments() {
		return ok(appointmentService.getAllAppointments());
	}

	@Override
	@GetMapping("/patient/{patientId}")
	public RootEntity<List<AppointmentDto>> getAppointmentsByPatientId(@Valid @PathVariable Long patientId) {
		return ok(appointmentService.getAppointmentsByPatientId(patientId));
	}

	@Override
	@GetMapping("/doctor/{doctorId}")
	public RootEntity<List<AppointmentDto>> getAppointmentsByDoctorId(@Valid @PathVariable Long doctorId) {
		return ok(appointmentService.getAppointmentsByDoctorId(doctorId));
	}

}

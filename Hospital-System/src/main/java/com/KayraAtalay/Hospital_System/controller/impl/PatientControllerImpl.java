package com.KayraAtalay.Hospital_System.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.KayraAtalay.Hospital_System.controller.IPatientController;
import com.KayraAtalay.Hospital_System.dto.PatientDto;
import com.KayraAtalay.Hospital_System.dto.PatientDtoIU;
import com.KayraAtalay.Hospital_System.entities.RootEntity;
import com.KayraAtalay.Hospital_System.service.IPatientService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/rest/api/patient")
public class PatientControllerImpl extends BaseController implements IPatientController {

	@Autowired
	private IPatientService patientService;

	@Override
	@PostMapping(path = "/save")
	public RootEntity<PatientDto> savePatient(@Valid @RequestBody PatientDtoIU patientDtoIU) {
		return ok(patientService.savePatient(patientDtoIU));
	}

	@Override
	@GetMapping(path = "/{id}")
	public RootEntity<PatientDto> findPatientById(@PathVariable(name = "id") Long id) {
		return ok(patientService.findPatientById(id));
	}

}

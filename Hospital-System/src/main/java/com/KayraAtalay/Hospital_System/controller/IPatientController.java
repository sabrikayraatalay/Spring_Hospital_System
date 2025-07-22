package com.KayraAtalay.Hospital_System.controller;

import com.KayraAtalay.Hospital_System.dto.PatientDto;
import com.KayraAtalay.Hospital_System.dto.PatientDtoIU;
import com.KayraAtalay.Hospital_System.entities.RootEntity;

public interface IPatientController {

	public RootEntity<PatientDto> savePatient(PatientDtoIU patientDtoIU);

	public RootEntity<PatientDto> findPatientById(Long id);
}

package com.KayraAtalay.Hospital_System.service;

import com.KayraAtalay.Hospital_System.dto.PatientDto;
import com.KayraAtalay.Hospital_System.dto.PatientDtoIU;

public interface IPatientService {

	public PatientDto savePatient(PatientDtoIU patientDtoIU);

	public PatientDto findPatientById(Long id);

}

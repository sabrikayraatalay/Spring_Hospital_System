package com.KayraAtalay.Hospital_System.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KayraAtalay.Hospital_System.dto.PatientDto;
import com.KayraAtalay.Hospital_System.dto.PatientDtoIU;
import com.KayraAtalay.Hospital_System.entities.Patient;
import com.KayraAtalay.Hospital_System.exception.BaseException;
import com.KayraAtalay.Hospital_System.exception.ErrorMessage;
import com.KayraAtalay.Hospital_System.exception.MessageType;
import com.KayraAtalay.Hospital_System.repository.PatientRepository;
import com.KayraAtalay.Hospital_System.service.IPatientService;

@Service
public class PatientServiceImpl implements IPatientService {

	@Autowired
	private PatientRepository patientRepository;

	@Override
	public PatientDto savePatient(PatientDtoIU patientDtoIU) {
		PatientDto dto = new PatientDto();
		Patient patient = new Patient();
		List<Patient> patients = patientRepository.findAll();

		for (Patient p : patients) {
			if (p.getEmail().equals(patientDtoIU.getEmail()) || p.getTcNo().equals(patientDtoIU.getTcNo())
					|| p.getPhone().equals(patientDtoIU.getPhone())) {
				throw new BaseException(
						new ErrorMessage(MessageType.INVALID_OPERATION, "This patient is already exist"));
			}
		}
		BeanUtils.copyProperties(patientDtoIU, patient);
		patientRepository.save(patient);
		BeanUtils.copyProperties(patient, dto);
		return dto;
	}

	@Override
	public PatientDto findPatientById(Long id) {
		Optional<Patient> optional = patientRepository.findById(id);

		if (optional.isPresent()) {
			Patient patient = optional.get();
			PatientDto dto = new PatientDto();
			BeanUtils.copyProperties(patient, dto);
			return dto;
		}
		throw new BaseException(new ErrorMessage(MessageType.NO_RECORDS_EXIST, id.toString()));
	}

}

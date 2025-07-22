package com.KayraAtalay.Hospital_System.service;

import java.util.List;

import com.KayraAtalay.Hospital_System.dto.DoctorDto;
import com.KayraAtalay.Hospital_System.dto.DoctorDtoIU;

public interface IDoctorService {
	
	 public DoctorDto saveDoctor(DoctorDtoIU doctorDtoIU);
	 
	 public void deleteDoctor(Long id);
	 
	 public DoctorDto findDoctorById(Long id);
	 
	 public List<DoctorDto> getAllDoctors();

}

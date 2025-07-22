package com.KayraAtalay.Hospital_System.controller;

import java.util.List;

import com.KayraAtalay.Hospital_System.dto.DoctorDto;
import com.KayraAtalay.Hospital_System.dto.DoctorDtoIU;
import com.KayraAtalay.Hospital_System.entities.RootEntity;

public interface IDoctorController {
	
	public RootEntity<DoctorDto> saveDoctor(DoctorDtoIU doctorDtoIU);

	public RootEntity<String> deleteDoctor(Long id);

	public RootEntity<DoctorDto> findDoctorById(Long id);

	public RootEntity<List<DoctorDto>> getAllDoctors();
}

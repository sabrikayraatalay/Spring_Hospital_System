package com.KayraAtalay.Hospital_System.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.KayraAtalay.Hospital_System.controller.IDoctorController;
import com.KayraAtalay.Hospital_System.dto.DoctorDto;
import com.KayraAtalay.Hospital_System.dto.DoctorDtoIU;
import com.KayraAtalay.Hospital_System.entities.RootEntity;
import com.KayraAtalay.Hospital_System.service.IDoctorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/rest/api/doctor")
public class DoctorControllerImpl extends BaseController  implements IDoctorController {
	
	@Autowired
	private IDoctorService doctorService;

	@Override
	@PostMapping(path = "/save")
	public RootEntity<DoctorDto> saveDoctor(@Valid @RequestBody DoctorDtoIU doctorDtoIU) {
		return ok(doctorService.saveDoctor(doctorDtoIU));
	}

	@Override
	@DeleteMapping(path = "/delete/{id}")
	public RootEntity<String> deleteDoctor(@PathVariable(name = "id") Long id){
		doctorService.deleteDoctor(id);
		return ok("Doctor deleted successfully");
	}

	@Override
	@GetMapping(path = "/{id}")
	public RootEntity<DoctorDto> findDoctorById(@PathVariable(name = "id") Long id) {
		return ok(doctorService.findDoctorById(id));
	}

	@Override
	@GetMapping(path = "/list")
	public RootEntity<List<DoctorDto>> getAllDoctors() {
		return ok(doctorService.getAllDoctors());
	}

}

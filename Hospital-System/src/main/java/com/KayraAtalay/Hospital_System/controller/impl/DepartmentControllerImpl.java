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

import com.KayraAtalay.Hospital_System.controller.IDepartmentController;
import com.KayraAtalay.Hospital_System.dto.DepartmentDto;
import com.KayraAtalay.Hospital_System.dto.DepartmentDtoIU;
import com.KayraAtalay.Hospital_System.dto.DoctorDto;
import com.KayraAtalay.Hospital_System.entities.RootEntity;
import com.KayraAtalay.Hospital_System.service.IDepartmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/rest/api/department")
public class DepartmentControllerImpl extends BaseController implements IDepartmentController {
	
	@Autowired
	private IDepartmentService departmentService;

	@Override
	@PostMapping(path = "/save")
	public RootEntity<DepartmentDto> saveDepartment(@Valid @RequestBody DepartmentDtoIU departmentDtoIU) {
		return ok(departmentService.saveDepartment(departmentDtoIU));
	}

	@Override
	@DeleteMapping(path = "/delete/{id}")
	public RootEntity<String> deleteDepartment(@PathVariable(name = "id")Long id) {
		departmentService.deleteDepartment(id);
		return ok("Department deleted Successfully");
	}

	@Override
	@GetMapping(path = "/list/{id}")
	public RootEntity<List<DoctorDto>> departmentList(@PathVariable(name = "id")Long id) {
		return ok(departmentService.departmentList(id));
	}

	@Override
	@GetMapping(path = "/list")
	public RootEntity<List<DepartmentDto>> getAllDepartments() {
		return ok(departmentService.getAllDepartments());
	}

}

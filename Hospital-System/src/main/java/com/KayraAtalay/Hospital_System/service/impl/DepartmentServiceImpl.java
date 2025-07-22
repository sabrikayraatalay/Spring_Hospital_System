package com.KayraAtalay.Hospital_System.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KayraAtalay.Hospital_System.dto.DepartmentDto;
import com.KayraAtalay.Hospital_System.dto.DepartmentDtoIU;
import com.KayraAtalay.Hospital_System.dto.DoctorDto;
import com.KayraAtalay.Hospital_System.entities.Department;
import com.KayraAtalay.Hospital_System.entities.Doctor;
import com.KayraAtalay.Hospital_System.exception.BaseException;
import com.KayraAtalay.Hospital_System.exception.ErrorMessage;
import com.KayraAtalay.Hospital_System.exception.MessageType;
import com.KayraAtalay.Hospital_System.repository.DepartmentRepository;
import com.KayraAtalay.Hospital_System.repository.DoctorRepository;
import com.KayraAtalay.Hospital_System.service.IDepartmentService;

@Service
public class DepartmentServiceImpl implements IDepartmentService {

	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	private DoctorRepository doctorRepository;

	@Override
	public DepartmentDto saveDepartment(DepartmentDtoIU departmentDtoIU) {
		DepartmentDto dto = new DepartmentDto();
		Department department = new Department();
		List<Department> departments = departmentRepository.findAll();
		for (Department findDepartment : departments) {
			if (findDepartment.getName().equalsIgnoreCase(departmentDtoIU.getName())) {
				throw new BaseException(new ErrorMessage(MessageType.INVALID_OPERATION,
						departmentDtoIU.getName() + " is already in the system"));
			}
		}
		BeanUtils.copyProperties(departmentDtoIU, department);
		departmentRepository.save(department);
		BeanUtils.copyProperties(department, dto);

		return dto;
	}

	@Override
	public void deleteDepartment(Long id) {
		Optional<Department> optional = departmentRepository.findById(id);

		if (optional.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.NO_RECORDS_EXIST, id.toString()));
		}

		Department department = optional.get();
		List<Doctor> doctors = new ArrayList<>(department.getDoctors());
		for (Doctor doctor : doctors) {
			department.getDoctors().remove(doctor);
			doctorRepository.delete(doctor);
		}
		departmentRepository.delete(department);

	}

	@Override
	public List<DoctorDto> departmentList(Long id) {
		List<DoctorDto> doctorsDto = new ArrayList<>();
		Optional<Department> optional = departmentRepository.findById(id);

		if (optional.isPresent()) {
			Department department = optional.get();

			List<Doctor> doctors = department.getDoctors();

			for (Doctor doctor : doctors) {
				DoctorDto doctorDto = new DoctorDto();
				doctorDto.setId(doctor.getId());
				doctorDto.setFirstName(doctor.getFirstName());
				doctorDto.setLastName(doctor.getLastName());
				doctorDto.setSpecialization(doctor.getSpecialization());
				doctorDto.setDepartmentName(doctor.getDepartment().getName());

				doctorsDto.add(doctorDto);
			}
			return doctorsDto;
		}

		throw new BaseException(new ErrorMessage(MessageType.NO_RECORDS_EXIST, id.toString()));
	}

	@Override
	public List<DepartmentDto> getAllDepartments() {
		List<Department> departments = departmentRepository.findAll();
		List<DepartmentDto> dtos = new ArrayList<>();

		if (departments.isEmpty()) {
			throw new BaseException(
					new ErrorMessage(MessageType.NO_RECORDS_EXIST, "There is no Department in the system"));
		}

		for (Department department : departments) {
			DepartmentDto departmentDto = new DepartmentDto();
			BeanUtils.copyProperties(department, departmentDto);

			List<DoctorDto> doctorDtos = new ArrayList<>();
			for (Doctor doctor : department.getDoctors()) {
				DoctorDto doctorDto = new DoctorDto();
				BeanUtils.copyProperties(doctor, doctorDto);
				doctorDto.setDepartmentName(department.getName());
				doctorDtos.add(doctorDto);
			}

			departmentDto.setDepartment_doctors(doctorDtos);
			dtos.add(departmentDto);
		}

		return dtos;
	}

}

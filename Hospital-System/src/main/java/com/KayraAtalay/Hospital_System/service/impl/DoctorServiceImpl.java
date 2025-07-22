package com.KayraAtalay.Hospital_System.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KayraAtalay.Hospital_System.dto.DepartmentDto;
import com.KayraAtalay.Hospital_System.dto.DoctorDto;
import com.KayraAtalay.Hospital_System.dto.DoctorDtoIU;
import com.KayraAtalay.Hospital_System.entities.Appointment;
import com.KayraAtalay.Hospital_System.entities.AppointmentStatus;
import com.KayraAtalay.Hospital_System.entities.Department;
import com.KayraAtalay.Hospital_System.entities.Doctor;
import com.KayraAtalay.Hospital_System.exception.BaseException;
import com.KayraAtalay.Hospital_System.exception.ErrorMessage;
import com.KayraAtalay.Hospital_System.exception.MessageType;
import com.KayraAtalay.Hospital_System.repository.AppointmentRepository;
import com.KayraAtalay.Hospital_System.repository.DepartmentRepository;
import com.KayraAtalay.Hospital_System.repository.DoctorRepository;
import com.KayraAtalay.Hospital_System.service.IDoctorService;

@Service
public class DoctorServiceImpl implements IDoctorService {

	@Autowired
	private DoctorRepository doctorRepository;

	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Override
	public DoctorDto saveDoctor(DoctorDtoIU doctorDtoIU) {
		Doctor doctor = new Doctor();
		DoctorDto dto = new DoctorDto();
		BeanUtils.copyProperties(doctorDtoIU, doctor);

		Optional<Department> optional = departmentRepository.findById(doctorDtoIU.getDepartmentId());

		if (optional.isPresent()) {
			Department department = optional.get();
			DepartmentDto departmentDto = new DepartmentDto();
			doctor.setDepartment(department);
			department.getDoctors().add(doctor);
			doctorRepository.save(doctor);
			BeanUtils.copyProperties(doctor, dto);
			dto.setDepartmentName(doctor.getDepartment().getName());
			BeanUtils.copyProperties(department, departmentDto);
			departmentDto.setName(department.getName());
			departmentDto.getDepartment_doctors().add(dto);
			return dto;
		}

		throw new BaseException(new ErrorMessage(MessageType.NO_RECORDS_EXIST,
				"There is not any department that have id: " + doctorDtoIU.getDepartmentId().toString()));
	}

	@Override
	public void deleteDoctor(Long id) {
		Optional<Doctor> optional = doctorRepository.findById(id);

		if (optional.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.NO_RECORDS_EXIST, id.toString()));
		}

		if (optional.isPresent()) {
			Doctor doctor = optional.get();

			List<Appointment> deletedAppointments = new ArrayList<>(doctor.getAppointments()); // Copy list for removing
																								// object	

			for (Appointment appointment : deletedAppointments) {
				appointment.setStatus(AppointmentStatus.CANCELLED);
				appointment.getPatient().getAppointments().remove(appointment);
			}

			appointmentRepository.saveAll(deletedAppointments);

			doctor.getDepartment().getDoctors().remove(doctor);

			doctorRepository.delete(doctor);

		}

	}

	@Override
	public DoctorDto findDoctorById(Long id) {
		Optional<Doctor> optional = doctorRepository.findById(id);

		if (optional.isPresent()) {
			Doctor doctor = optional.get();
			DoctorDto dto = new DoctorDto();

			BeanUtils.copyProperties(doctor, dto);
			dto.setDepartmentName(doctor.getDepartment().getName());

			return dto;
		}
		throw new BaseException(new ErrorMessage(MessageType.NO_RECORDS_EXIST, id.toString()));
	}

	@Override
	public List<DoctorDto> getAllDoctors() {
		List<Doctor> doctors = doctorRepository.findAll();
		List<DoctorDto> dtos = new ArrayList<>();

		for (Doctor doctor : doctors) {
			DoctorDto dto = new DoctorDto();
			BeanUtils.copyProperties(doctor, dto);
			dto.setDepartmentName(doctor.getDepartment().getName());
			dtos.add(dto);
		}
		if (dtos.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.NO_RECORDS_EXIST, "There is no doctor in the system"));
		}
		return dtos;
	}

}

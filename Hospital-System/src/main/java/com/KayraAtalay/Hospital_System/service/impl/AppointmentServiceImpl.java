package com.KayraAtalay.Hospital_System.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KayraAtalay.Hospital_System.dto.AppointmentDto;
import com.KayraAtalay.Hospital_System.dto.AppointmentDtoIU;
import com.KayraAtalay.Hospital_System.entities.Appointment;
import com.KayraAtalay.Hospital_System.entities.AppointmentStatus;
import com.KayraAtalay.Hospital_System.entities.Doctor;
import com.KayraAtalay.Hospital_System.entities.Patient;
import com.KayraAtalay.Hospital_System.exception.BaseException;
import com.KayraAtalay.Hospital_System.exception.ErrorMessage;
import com.KayraAtalay.Hospital_System.exception.MessageType;
import com.KayraAtalay.Hospital_System.repository.AppointmentRepository;
import com.KayraAtalay.Hospital_System.repository.DoctorRepository;
import com.KayraAtalay.Hospital_System.repository.PatientRepository;
import com.KayraAtalay.Hospital_System.service.IAppointmentService;

@Service
public class AppointmentServiceImpl implements IAppointmentService {

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private DoctorRepository doctorRepository;

	@Autowired
	private PatientRepository patientRepository;

	@Override
	public AppointmentDto createAppointment(AppointmentDtoIU appointmentDtoIU) {
		Optional<Patient> patientOptional = patientRepository.findById(appointmentDtoIU.getPatientId());
		Optional<Doctor> doctorOptional = doctorRepository.findById(appointmentDtoIU.getDoctorId());

		if (patientOptional.isPresent() && doctorOptional.isPresent()) {
			Doctor doctor = doctorOptional.get();
			Patient patient = patientOptional.get();
			Appointment appointment = new Appointment();
			AppointmentDto dto = new AppointmentDto();
			List<Appointment> doctorAppointments = doctor.getAppointments();
			List<Appointment> patientAppointments = patient.getAppointments();

			// checking patient appointments for date
			for (Appointment p : patientAppointments) {
				if (p.getAppointmentTime().isEqual(appointmentDtoIU.getAppointmentTime()))
					throw new BaseException(
							new ErrorMessage(MessageType.TIME_CONFLICT, p.getAppointmentTime().toString() + " "
									+ ": Patient has another appointment in this date"));
			}

			// checking doctor appointments for date
			for (Appointment p : doctorAppointments) {
				if (p.getAppointmentTime().isEqual(appointmentDtoIU.getAppointmentTime())) {
					throw new BaseException(new ErrorMessage(MessageType.TIME_CONFLICT,
							p.getAppointmentTime().toString() + " " + ": Doctor has another appointment in this date"));
				}
			}

			BeanUtils.copyProperties(appointmentDtoIU, appointment);
			appointment.setDoctor(doctor);
			appointment.setPatient(patient);
			appointment.setStatus(AppointmentStatus.SCHEDULED);

			appointmentRepository.save(appointment);

			BeanUtils.copyProperties(appointment, dto);
			dto.setDoctorFullName(doctor.getFirstName() + " " + doctor.getLastName());
			dto.setPatientFullName(patient.getFirstName() + " " + patient.getLastName());
			dto.setDepartmentName(doctor.getDepartment().getName());
			dto.setDoctorSpecialization(doctor.getSpecialization());

			return dto;
		}
		throw new BaseException(new ErrorMessage(MessageType.NO_RECORDS_EXIST, "doctor or patient"));
	}

	@Override
	public AppointmentDto updateAppointment(Long id, LocalDateTime dateTime) {
		Optional<Appointment> optional = appointmentRepository.findById(id);

		if (optional.isPresent()) {
			Appointment appointment = optional.get();
			Doctor doctor = appointment.getDoctor();

			if (appointment.getAppointmentTime().isEqual(dateTime)) {
				throw new BaseException(
						new ErrorMessage(MessageType.INVALID_OPERATION, "You can not change the date to same date"));
			}

			for (Appointment p : doctor.getAppointments()) {
				if (!p.getId().equals(id) && p.getAppointmentTime().isEqual(dateTime)) {
					throw new BaseException(new ErrorMessage(MessageType.INVALID_OPERATION,
							"There is another appointment in this date"));
				}
			}

			AppointmentDto dto = new AppointmentDto();
			appointment.setAppointmentTime(dateTime);
			Doctor doctor2 = appointment.getDoctor();
			Patient patient = appointment.getPatient();
			BeanUtils.copyProperties(appointment, dto);

			dto.setDoctorFullName(doctor2.getFirstName() + " " + doctor2.getLastName());
			dto.setPatientFullName(patient.getFirstName() + " " + patient.getLastName());
			dto.setDepartmentName(doctor2.getDepartment().getName());
			dto.setDoctorSpecialization(doctor2.getSpecialization());

			appointmentRepository.save(appointment);

			return dto;

		}
		throw new BaseException(new ErrorMessage(MessageType.NO_RECORDS_EXIST,
				"There is not any appointment that have id: " + id.toString()));
	}

	@Override
	public AppointmentDto cancelAppointment(Long id) {
		Optional<Appointment> optional = appointmentRepository.findById(id);

		if (optional.isPresent()) {
			AppointmentDto dto = new AppointmentDto();
			Appointment appointment = optional.get();

			if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
				throw new BaseException(
						new ErrorMessage(MessageType.INVALID_OPERATION, "This appointment is already cancelled"));
			}
			appointment.getPatient().getAppointments().remove(appointment);
			appointment.getDoctor().getAppointments().remove(appointment);
			appointment.setStatus(AppointmentStatus.CANCELLED);

			appointmentRepository.save(appointment);

			Doctor doctor = appointment.getDoctor();
			Patient patient = appointment.getPatient();

			BeanUtils.copyProperties(appointment, dto);

			dto.setDoctorFullName(doctor.getFirstName() + " " + doctor.getLastName());
			dto.setPatientFullName(patient.getFirstName() + " " + patient.getLastName());
			dto.setDepartmentName(doctor.getDepartment().getName());
			dto.setDoctorSpecialization(doctor.getSpecialization());

			return dto;
		}
		throw new BaseException(new ErrorMessage(MessageType.NO_RECORDS_EXIST, id.toString()));
	}

	@Override
	public AppointmentDto getAppointmentById(Long id) {
		Optional<Appointment> optional = appointmentRepository.findById(id);

		if (optional.isPresent()) {
			Appointment appointment = optional.get();
			AppointmentDto dto = new AppointmentDto();
			Doctor doctor = appointment.getDoctor();
			Patient patient = appointment.getPatient();

			BeanUtils.copyProperties(appointment, dto);

			dto.setDoctorFullName(doctor.getFirstName() + " " + doctor.getLastName());
			dto.setPatientFullName(patient.getFirstName() + " " + patient.getLastName());
			dto.setDepartmentName(doctor.getDepartment().getName());
			dto.setDoctorSpecialization(doctor.getSpecialization());

			return dto;

		}
		throw new BaseException(new ErrorMessage(MessageType.NO_RECORDS_EXIST, id.toString()));
	}

	@Override
	public List<AppointmentDto> getAllAppointments() {
		List<Appointment> appointments = appointmentRepository.findAll();
		List<AppointmentDto> dtos = new ArrayList<>();

		if (!appointments.isEmpty()) {
			for (Appointment appointment : appointments) {
				AppointmentDto dto = new AppointmentDto();
				Doctor doctor = appointment.getDoctor();
				Patient patient = appointment.getPatient();
				BeanUtils.copyProperties(appointment, dto);

				dto.setDoctorFullName(doctor.getFirstName() + " " + doctor.getLastName());
				dto.setPatientFullName(patient.getFirstName() + " " + patient.getLastName());
				dto.setDepartmentName(doctor.getDepartment().getName());
				dto.setDoctorSpecialization(doctor.getSpecialization());

				dtos.add(dto);
			}
			return dtos;
		}
		throw new BaseException(new ErrorMessage(MessageType.NO_RECORDS_EXIST, "There are no Appointments"));
	}

	@Override
	public List<AppointmentDto> getAppointmentsByPatientId(Long patientId) {
		Optional<Patient> optional = patientRepository.findById(patientId);
		List<AppointmentDto> dtos = new ArrayList<>();

		if (optional.isPresent()) {
			Patient patient = optional.get();
			List<Appointment> appointments = patient.getAppointments();

			if (appointments.isEmpty()) {
				throw new BaseException(new ErrorMessage(MessageType.NO_RECORDS_EXIST,
						patient.getFirstName() + " " + patient.getLastName() + "does not have any appointments"));
			}

			for (Appointment appointment : appointments) {
				AppointmentDto dto = new AppointmentDto();
				Doctor doctor = appointment.getDoctor();
				BeanUtils.copyProperties(appointment, dto);
				dto.setDoctorFullName(doctor.getFirstName() + " " + doctor.getLastName());
				dto.setPatientFullName(patient.getFirstName() + " " + patient.getLastName());
				dto.setDepartmentName(doctor.getDepartment().getName());
				dto.setDoctorSpecialization(doctor.getSpecialization());

				dtos.add(dto);

			}
			return dtos;
		}

		throw new BaseException(new ErrorMessage(MessageType.NO_RECORDS_EXIST,
				"There is not any patient that have id: " + patientId.toString()));

	}

	@Override
	public List<AppointmentDto> getAppointmentsByDoctorId(Long doctorId) {
		Optional<Doctor> optional = doctorRepository.findById(doctorId);
		List<AppointmentDto> dtos = new ArrayList<>();

		if (optional.isPresent()) {
			Doctor doctor = optional.get();
			List<Appointment> appointments = doctor.getAppointments();

			if (appointments.isEmpty()) {
				throw new BaseException(new ErrorMessage(MessageType.NO_RECORDS_EXIST,
						doctor.getFirstName() + " " + doctor.getLastName() + "does not have any appointments"));
			}

			for (Appointment appointment : appointments) {
				AppointmentDto dto = new AppointmentDto();
				Patient patient = appointment.getPatient();
				BeanUtils.copyProperties(appointment, dto);
				dto.setDepartmentName(doctor.getDepartment().getName());
				dto.setDoctorFullName(doctor.getFirstName() + " " + doctor.getLastName());
				dto.setPatientFullName(patient.getFirstName() + " " + patient.getLastName());
				dto.setDoctorSpecialization(doctor.getSpecialization());
				dtos.add(dto);
			}
			return dtos;
		}
		throw new BaseException(new ErrorMessage(MessageType.NO_RECORDS_EXIST,
				"There is not any doctor that have id: " + doctorId.toString()));
	}

	@Override
	public AppointmentDto completeAppointment(Long id) {
		Optional<Appointment> optional = appointmentRepository.findById(id);

		if (optional.isPresent()) {
			Appointment appointment = optional.get();
			if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
				throw new BaseException(
						new ErrorMessage(MessageType.INVALID_STATUS_CHANGE, "This appointment is cancelled "));
			}
			if (appointment.getStatus() == AppointmentStatus.COMPLETED) {
				throw new BaseException(
						new ErrorMessage(MessageType.INVALID_STATUS_CHANGE, "This appointment is already completed "));
			}
			AppointmentDto dto = new AppointmentDto();

			appointment.setStatus(AppointmentStatus.COMPLETED);
			appointmentRepository.save(appointment);

			Doctor doctor = appointment.getDoctor();
			Patient patient = appointment.getPatient();
			BeanUtils.copyProperties(appointment, dto);

			dto.setDoctorFullName(doctor.getFirstName() + " " + doctor.getLastName());
			dto.setPatientFullName(patient.getFirstName() + " " + patient.getLastName());
			dto.setDepartmentName(doctor.getDepartment().getName());
			dto.setDoctorSpecialization(doctor.getSpecialization());

			return dto;

		}
		throw new BaseException(new ErrorMessage(MessageType.NO_RECORDS_EXIST,
				"There is not any appointment that have id: " + id.toString()));
	}

}

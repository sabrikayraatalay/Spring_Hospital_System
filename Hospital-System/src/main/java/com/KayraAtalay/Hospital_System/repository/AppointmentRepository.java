package com.KayraAtalay.Hospital_System.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.KayraAtalay.Hospital_System.entities.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long>{

}

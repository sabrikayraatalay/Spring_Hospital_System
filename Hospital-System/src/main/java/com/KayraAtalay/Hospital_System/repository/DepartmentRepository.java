package com.KayraAtalay.Hospital_System.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.KayraAtalay.Hospital_System.entities.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

}

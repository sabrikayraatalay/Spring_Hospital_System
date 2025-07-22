package com.KayraAtalay.Hospital_System.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "patient")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "first_name", nullable = false)
	private String firstName;
	
	@Column(name = "last_name", nullable = false)
	private String lastName;
	
	@Column(name = "tcNo", nullable = false)
	private String tcNo;
	
	@Column(name = "phone", nullable = false)
	private String phone;
	
	@Column(name = "email", nullable = false)
	private String  email;
	
	@OneToMany(mappedBy = "patient")
	private List<Appointment> appointments = new ArrayList<>();
	

	
}

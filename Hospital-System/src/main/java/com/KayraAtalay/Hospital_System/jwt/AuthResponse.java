package com.KayraAtalay.Hospital_System.jwt;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
	
	private String accessToken;
	
	private String refreshToken;

}

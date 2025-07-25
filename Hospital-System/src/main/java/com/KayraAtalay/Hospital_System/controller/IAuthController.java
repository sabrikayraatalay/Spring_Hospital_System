package com.KayraAtalay.Hospital_System.controller;

import com.KayraAtalay.Hospital_System.dto.UserDto;
import com.KayraAtalay.Hospital_System.entities.RootEntity;
import com.KayraAtalay.Hospital_System.jwt.AuthRequest;
import com.KayraAtalay.Hospital_System.jwt.AuthResponse;
import com.KayraAtalay.Hospital_System.jwt.RefreshTokenRequest;


public interface IAuthController {
	
	public RootEntity<UserDto> register(AuthRequest request);

	public RootEntity<AuthResponse> authenticate(AuthRequest request);

	public RootEntity<AuthResponse> refreshToken(RefreshTokenRequest request);
}

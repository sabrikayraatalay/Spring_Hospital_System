package com.KayraAtalay.Hospital_System.service;

import com.KayraAtalay.Hospital_System.jwt.AuthResponse;
import com.KayraAtalay.Hospital_System.jwt.RefreshTokenRequest;

public interface IRefreshTokenService {
	
	public AuthResponse refreshToken(RefreshTokenRequest request);

}
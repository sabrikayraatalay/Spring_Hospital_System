package com.KayraAtalay.Hospital_System.service;


import com.KayraAtalay.Hospital_System.dto.UserDto;
import com.KayraAtalay.Hospital_System.jwt.*;

public interface IAuthService {
	
	public UserDto register(AuthRequest request);

    public AuthResponse authenticate(AuthRequest request);


}

package com.KayraAtalay.Hospital_System.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.KayraAtalay.Hospital_System.controller.IAuthController;
import com.KayraAtalay.Hospital_System.dto.UserDto;
import com.KayraAtalay.Hospital_System.entities.RootEntity;
import com.KayraAtalay.Hospital_System.jwt.AuthRequest;
import com.KayraAtalay.Hospital_System.jwt.AuthResponse;
import com.KayraAtalay.Hospital_System.jwt.RefreshTokenRequest;
import com.KayraAtalay.Hospital_System.service.IAuthService;
import com.KayraAtalay.Hospital_System.service.IRefreshTokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/rest/api")
public class AuthControllerImpl extends BaseController implements IAuthController{
	
	@Autowired
	private IAuthService authService;
	
	@Autowired
	private IRefreshTokenService refreshTokenService;

	@PostMapping(path = "/register")
	@Override
	public RootEntity<UserDto> register(@Valid @RequestBody AuthRequest request) {
				return ok(authService.register(request));
	}

	@PostMapping(path = "/authenticate")
	@Override
	public RootEntity<AuthResponse> authenticate(@Valid @RequestBody AuthRequest request) {
				return ok(authService.authenticate(request));
	}

	@PostMapping(path = "/refreshtoken")
	@Override
	public RootEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
		return ok(refreshTokenService.refreshToken(request));
	}

}

package com.KayraAtalay.Hospital_System.service.impl;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KayraAtalay.Hospital_System.entities.RefreshToken;
import com.KayraAtalay.Hospital_System.entities.User;
import com.KayraAtalay.Hospital_System.exception.BaseException;
import com.KayraAtalay.Hospital_System.exception.ErrorMessage;
import com.KayraAtalay.Hospital_System.exception.MessageType;
import com.KayraAtalay.Hospital_System.jwt.AuthResponse;
import com.KayraAtalay.Hospital_System.jwt.JwtService;
import com.KayraAtalay.Hospital_System.jwt.RefreshTokenRequest;
import com.KayraAtalay.Hospital_System.repository.RefreshTokenRepository;
import com.KayraAtalay.Hospital_System.service.IRefreshTokenService;


@Service
public class RefreshTokenServiceImpl implements IRefreshTokenService {

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	@Autowired
	private JwtService jwtService;

	public boolean isRefreshTokenExpired(Date expiredDate) {
		return new Date().before(expiredDate);
	}

	private RefreshToken createRefreshToken(User user) {
		RefreshToken refreshToken = new RefreshToken();

		refreshToken.setRefreshToken(UUID.randomUUID().toString());
		refreshToken.setExpireDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 4));
		refreshToken.setUser(user);

		return refreshToken;
	}

	@Override
	public AuthResponse refreshToken(RefreshTokenRequest request) {
		Optional<RefreshToken> optional = refreshTokenRepository.findByRefreshToken(request.getRefreshToken());

		if (optional.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.WRONG_TOKEN, 
					"Could not find this Refresh Token"));
		}

		RefreshToken refreshToken = optional.get();

		if (!isRefreshTokenExpired(refreshToken.getExpireDate())) {
			throw new BaseException(new ErrorMessage(MessageType.TOKEN_EXPIRED, 
					refreshToken.getRefreshToken()));
		}

		String accessToken = jwtService.generateToken(refreshToken.getUser());

		RefreshToken savedRefreshToken = refreshTokenRepository.save(createRefreshToken(refreshToken.getUser()));

		return new AuthResponse(accessToken, savedRefreshToken.getRefreshToken());
	}

}




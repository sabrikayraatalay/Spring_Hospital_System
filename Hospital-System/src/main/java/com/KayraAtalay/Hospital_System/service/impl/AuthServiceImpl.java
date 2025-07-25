package com.KayraAtalay.Hospital_System.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.KayraAtalay.Hospital_System.dto.UserDto;
import com.KayraAtalay.Hospital_System.entities.RefreshToken;
import com.KayraAtalay.Hospital_System.entities.User;
import com.KayraAtalay.Hospital_System.exception.BaseException;
import com.KayraAtalay.Hospital_System.exception.ErrorMessage;
import com.KayraAtalay.Hospital_System.exception.MessageType;
import com.KayraAtalay.Hospital_System.jwt.AuthRequest;
import com.KayraAtalay.Hospital_System.jwt.AuthResponse;
import com.KayraAtalay.Hospital_System.jwt.JwtService;
import com.KayraAtalay.Hospital_System.repository.RefreshTokenRepository;
import com.KayraAtalay.Hospital_System.repository.UserRepository;
import com.KayraAtalay.Hospital_System.service.IAuthService;




@Service
public class AuthServiceImpl implements IAuthService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationProvider authenticationProvider;

	@Autowired
	private JwtService jwtService;
	
	
	@Autowired
	private RefreshTokenRepository refreshTokenRepository;
	
	
	private RefreshToken createRefreshToken(User user) {
		RefreshToken refreshToken = new RefreshToken();
		
		refreshToken.setRefreshToken(UUID.randomUUID().toString());
		refreshToken.setExpireDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 4));
		refreshToken.setUser(user);
		
		return refreshToken;
	}
	

	@Override
	public AuthResponse authenticate(AuthRequest request) {
		try {
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken
					(request.getUsername(),
					request.getPassword());

			authenticationProvider.authenticate(auth);

			Optional<User> optionalUser = userRepository.findByUsername(request.getUsername());
			String accessToken = jwtService.generateToken(optionalUser.get());
			
			RefreshToken refreshToken = createRefreshToken(optionalUser.get());
			refreshTokenRepository.save(refreshToken);
			
			return new AuthResponse(accessToken, refreshToken.getRefreshToken());
			
		} catch (Exception e) {
			throw new BaseException(new ErrorMessage(MessageType.INVALID_OPERATION, 
					"Wrong username or password"));
		}
	}

	@Override
	public UserDto register(AuthRequest request) {
		
		List<User> users = userRepository.findAll();
		
		User user = new User();
		user.setUsername(request.getUsername());
		user.setPassword(passwordEncoder.encode(request.getPassword()));

		for (User findUser : users) {
			if(findUser.getUsername().equalsIgnoreCase(user.getUsername())) {
				throw new BaseException(new ErrorMessage(MessageType.INVALID_OPERATION,
						"This user already exists"));
			}
		}
		
		User savedUser = userRepository.save(user);

		UserDto dtoUser = new UserDto();

		BeanUtils.copyProperties(savedUser, dtoUser);

		return dtoUser;
	}

}

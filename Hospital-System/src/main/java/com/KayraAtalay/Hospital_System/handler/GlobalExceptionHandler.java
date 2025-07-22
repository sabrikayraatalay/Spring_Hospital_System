package com.KayraAtalay.Hospital_System.handler;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.KayraAtalay.Hospital_System.exception.BaseException;

@ControllerAdvice
public class GlobalExceptionHandler {

	// BaseException: Custom exception
	@ExceptionHandler(value = { BaseException.class })
	public ResponseEntity<ApiError> handleBaseException(BaseException exception, WebRequest request) {
		return ResponseEntity.badRequest().body(createApiError(exception.getMessage(), request));
	}

	@ExceptionHandler(value = IllegalArgumentException.class)
	public ResponseEntity<ApiError<Map<String, List<String>>>> handleIllegalArgumentException(
			IllegalArgumentException exception, WebRequest request) {

		Map<String, List<String>> errorsMap = new HashMap<>();
		errorsMap.put("error", addMapValue(new ArrayList<>(), exception.getMessage()));

		return ResponseEntity.badRequest().body(createApiError(errorsMap, request));
	}

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<ApiError<Map<String, List<String>>>> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException exception, WebRequest request) {

		Map<String, List<String>> errorsMap = new HashMap<>();
		for (ObjectError objectError : exception.getBindingResult().getAllErrors()) {
			String fieldName = ((FieldError) objectError).getField();

			if (errorsMap.containsKey(fieldName)) {
				errorsMap.put(fieldName, addMapValue(errorsMap.get(fieldName), objectError.getDefaultMessage()));
			} else {
				errorsMap.put(fieldName, addMapValue(new ArrayList<>(), objectError.getDefaultMessage()));
			}
		}
		return ResponseEntity.badRequest().body(createApiError(errorsMap, request));
	}

	// Validation & IllegalArgumentException
	public <E> ApiError<E> createApiError(E message) {
		ApiError<E> apiError = new ApiError<>();
		apiError.setStatus(HttpStatus.BAD_REQUEST.value());

		Exception<E> exception = new Exception<>();
		exception.setCreateTime(new Date());
		exception.setHostName(getHostName());
		exception.setPath(null); // No Request
		exception.setMessage(message);

		apiError.setException(exception);

		return apiError;
	}

	public <E> ApiError<E> createApiError(E message, WebRequest request) {
		ApiError<E> apiError = new ApiError<>();
		apiError.setStatus(HttpStatus.BAD_REQUEST.value());

		Exception<E> exception = new Exception<>();
		exception.setCreateTime(new Date());
		exception.setHostName(getHostName());
		exception.setPath(request.getDescription(false).substring(4));
		exception.setMessage(message);

		apiError.setException(exception);

		return apiError;
	}

	private List<String> addMapValue(List<String> list, String newValue) {
		list.add(newValue);
		return list;
	}

	private String getHostName() {
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			System.out.println("Error occurred: " + e.getMessage());
			return null;
		}
	}
}

package com.KayraAtalay.Hospital_System.handler;

import lombok.Data;

@Data
public class ApiError<E> { // Generic 

	private Integer status;

	private Exception<E> exception;
}

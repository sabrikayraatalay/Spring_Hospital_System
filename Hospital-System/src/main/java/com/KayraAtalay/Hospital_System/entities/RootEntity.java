package com.KayraAtalay.Hospital_System.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RootEntity<T> {

	private boolean result;

	private String errorMessage; // if result is false, there will be a error message

	private T data; // if result is true, there will be a data in output

	
	public static <T> RootEntity<T> ok(T data) {

		RootEntity<T> rootEntity = new RootEntity<>();
		rootEntity.setData(data);
		rootEntity.setResult(true);
		rootEntity.setErrorMessage(null);
		return rootEntity;
	}

}

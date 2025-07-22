package com.KayraAtalay.Hospital_System.controller.impl;

import com.KayraAtalay.Hospital_System.entities.RootEntity;

public class BaseController {
	

	 public <T> RootEntity<T> ok(T data){
		return RootEntity.ok(data);
	 }
	 
	 public <T> RootEntity<T> error(String errorMessage){
		 return RootEntity.error(errorMessage);
	 }

}

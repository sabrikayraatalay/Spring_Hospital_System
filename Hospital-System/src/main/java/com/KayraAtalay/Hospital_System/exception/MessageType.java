package com.KayraAtalay.Hospital_System.exception;

import lombok.Getter;

@Getter
public enum MessageType {
	
	NO_RECORDS_EXIST("404", "Could not find the record"),
	TIME_CONFLICT("1002", "Appointment time conflict"),
	INVALID_OPERATION("1003", "Operation not allowed"),
	INVALID_STATUS_CHANGE("1004", "Status change is not valid"),
	TOKEN_EXPIRED("1005", "Token is expired"),
	WRONG_TOKEN("1006", "This token is not exist");
	
	
	private String code;
	
	private String message;
	
	   MessageType(String code, String message){
		   
		   this.code = code;
		   
		   this.message = message;
		
	}
	
	

}

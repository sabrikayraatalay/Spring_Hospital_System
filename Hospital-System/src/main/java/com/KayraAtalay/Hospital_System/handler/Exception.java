package com.KayraAtalay.Hospital_System.handler;

import java.util.Date;

import lombok.Data;

@Data
public class Exception<E> {
	
private String hostName;
	
    private String path;
    
    private Date createTime;
    
    private E message;

}

package com.KayraAtalay.Hospital_System.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.KayraAtalay.Hospital_System.configuration.DataSource;
import com.KayraAtalay.Hospital_System.configuration.GlobalProperties;
import com.KayraAtalay.Hospital_System.configuration.Server;

@RestController
@RequestMapping(path = "/rest/api/property")
public class PropertySourceController {

	@Autowired
	private GlobalProperties globalProperties;
	
	
	@GetMapping(path = "/datasource")
	public DataSource getDataSource() {
		return null;
	}
	
	@GetMapping(path = "/getServers")
	public List<Server> getServer(){
		System.out.println("Key value is: " + globalProperties.getKey());
		return globalProperties.getServers();
	}
}

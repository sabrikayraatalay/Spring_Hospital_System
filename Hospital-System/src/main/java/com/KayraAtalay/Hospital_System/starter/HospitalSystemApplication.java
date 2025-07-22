package com.KayraAtalay.Hospital_System.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.KayraAtalay.Hospital_System.configuration.GlobalProperties;

@SpringBootApplication
@ComponentScan(basePackages = { "com.KayraAtalay" })
@EntityScan(basePackages = { "com.KayraAtalay" })
@EnableJpaRepositories(basePackages = { "com.KayraAtalay" })
@EnableScheduling
//@PropertySource(value = "classpath:app.properties")
@EnableConfigurationProperties(value = GlobalProperties.class)
public class HospitalSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospitalSystemApplication.class, args);
	}

}

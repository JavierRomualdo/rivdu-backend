package com.rivdu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.rivdu.dao"})
@EntityScan(basePackages = {"com.rivdu"})
@EnableTransactionManagement
public class RivduApplication {

	public static void main(String[] args) {
		SpringApplication.run(RivduApplication.class, args);
	}

	//hola
}

package com.rajeshkawali;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
/**
 * @author Rajesh_Kawali
 *
 */
@EnableCaching
@SpringBootApplication
public class SpringbootMongodbRadisApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootMongodbRadisApplication.class, args);
	}

}

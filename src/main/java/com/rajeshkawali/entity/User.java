package com.rajeshkawali.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Rajesh_Kawali
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "user")
public class User {

	@Id
	private Long id;
	private String firstName;
	private String lastName;
	private Integer age;
	private String gender;
	private String role;
}
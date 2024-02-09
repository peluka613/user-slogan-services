package com.mango.customer.application.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

	@NotBlank(message = "Name is required")
	@Size(max = 100, message = "Name must be less than 100 characters")
	private String name;

	@NotBlank(message = "Last name is required")
	@Size(max = 100, message = "Last name must be less than 100 characters")
	private String lastname;

	@Size(max = 200, message = "Address must be less than 200 characters")
	private String address;

	@Size(max = 100, message = "City must be less than 100 characters")
	private String city;

	@NotBlank(message = "Email is required")
	@Email(message = "Email should be valid")
	private String email;
}

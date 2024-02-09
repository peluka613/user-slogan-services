package com.mango.customer.application.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
	private String name;
	private String lastname;
	private String address;
	private String city;
	private String email;
}

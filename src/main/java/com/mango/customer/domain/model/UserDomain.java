package com.mango.customer.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDomain {
	private String name;
	private String lastname;
	private String address;
	private String city;
	private String email;
}

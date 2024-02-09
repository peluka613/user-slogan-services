package com.mango.customer.application.service;

import com.mango.customer.application.mapper.UserDtoMapper;
import com.mango.customer.application.model.UserDTO;
import com.mango.customer.domain.model.UserDomain;
import com.mango.customer.domain.port.UserRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

	private final UserRepositoryPort userRepositoryPort;
	private final UserDtoMapper mapper;
	public UserDTO createUser(UserDTO user) {
		UserDomain userDomain = userRepositoryPort.createUser(mapper.toDomain(user));
		return mapper.toDto(userDomain);
	}

	public UserDTO updateUser(UserDTO user, String email) {
		UserDomain userDomain = userRepositoryPort.updateUser(mapper.toDomain(user), email);
		return mapper.toDto(userDomain);
	}

	public UserDTO findUserByEmail(String email) {
		UserDomain userDomain = userRepositoryPort.findUserByEmail(email);
		return mapper.toDto(userDomain);
	}
}

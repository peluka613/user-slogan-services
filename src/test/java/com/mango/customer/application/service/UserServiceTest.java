package com.mango.customer.application.service;

import com.mango.customer.application.mapper.UserDtoMapper;
import com.mango.customer.application.model.UserDTO;
import com.mango.customer.domain.model.UserDomain;
import com.mango.customer.domain.port.UserRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UserRepositoryPort userRepositoryPort;

	@Mock
	private UserDtoMapper mapper;

	@InjectMocks
	private UserService userService;

	@Test
	void createUser_Success() {
		UserDTO userDTO = new UserDTO();
		UserDomain userDomain = new UserDomain();
		when(mapper.toDomain(any(UserDTO.class))).thenReturn(userDomain);
		when(userRepositoryPort.createUser(any(UserDomain.class))).thenReturn(userDomain);
		when(mapper.toDto(any(UserDomain.class))).thenReturn(userDTO);

		UserDTO result = userService.createUser(userDTO);

		assertNotNull(result);
		verify(userRepositoryPort).createUser(userDomain);
		verify(mapper).toDto(userDomain);
	}

	@Test
	void updateUser_Success() {
		String email = "test@example.com";
		UserDTO userDTO = new UserDTO();
		UserDomain userDomain = new UserDomain();
		when(mapper.toDomain(any(UserDTO.class))).thenReturn(userDomain);
		when(userRepositoryPort.updateUser(any(UserDomain.class), eq(email))).thenReturn(userDomain);
		when(mapper.toDto(any(UserDomain.class))).thenReturn(userDTO);

		UserDTO result = userService.updateUser(userDTO, email);

		assertNotNull(result);
		verify(userRepositoryPort).updateUser(userDomain, email);
		verify(mapper).toDto(userDomain);
	}

	@Test
	void findUserByEmail_Success() {
		String email = "test@example.com";
		UserDomain userDomain = new UserDomain();
		UserDTO userDTO = new UserDTO();
		when(userRepositoryPort.findUserByEmail(email)).thenReturn(userDomain);
		when(mapper.toDto(any(UserDomain.class))).thenReturn(userDTO);

		UserDTO result = userService.findUserByEmail(email);

		assertNotNull(result);
		verify(userRepositoryPort).findUserByEmail(email);
		verify(mapper).toDto(userDomain);
	}

	@Test
	void createUser_UserRepositoryPortThrowsException() {
		UserDTO userDTO = new UserDTO();
		when(mapper.toDomain(any(UserDTO.class))).thenReturn(new UserDomain());
		when(userRepositoryPort.createUser(any(UserDomain.class))).thenThrow(new RuntimeException("Error creating user"));

		assertThrows(RuntimeException.class, () -> userService.createUser(userDTO));
	}

	@Test
	void updateUser_UserNotFound() {
		String email = "nonexistent@example.com";
		UserDTO userDTO = new UserDTO();
		when(mapper.toDomain(any(UserDTO.class))).thenReturn(new UserDomain());
		when(userRepositoryPort.updateUser(any(UserDomain.class), eq(email))).thenThrow(new RuntimeException("User not found"));

		assertThrows(RuntimeException.class, () -> userService.updateUser(userDTO, email));
	}

	@Test
	void findUserByEmail_UserNotFound() {
		String email = "nonexistent@example.com";
		when(userRepositoryPort.findUserByEmail(email)).thenThrow(new RuntimeException("User not found"));

		assertThrows(RuntimeException.class, () -> userService.findUserByEmail(email));
	}
}

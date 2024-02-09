package com.mango.customer.application.service;

import com.mango.customer.application.mapper.SloganDtoMapper;
import com.mango.customer.application.model.SloganDTO;
import com.mango.customer.domain.model.SloganDomain;
import com.mango.customer.domain.port.SloganRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SloganServiceTest {

	@Mock
	private SloganRepositoryPort sloganRepositoryPort;

	@Mock
	private SloganDtoMapper mapper;

	@InjectMocks
	private SloganService service;

	@Test
	void createSlogan_Success() {
		SloganDTO inputDto = new SloganDTO();
		SloganDomain domain = new SloganDomain();
		SloganDTO expectedDto = new SloganDTO();

		when(mapper.toDomain(any(SloganDTO.class))).thenReturn(domain);
		when(sloganRepositoryPort.createSlogan(any(SloganDomain.class))).thenReturn(domain);
		when(mapper.toDto(any(SloganDomain.class))).thenReturn(expectedDto);

		SloganDTO result = service.createSlogan(inputDto);

		assertNotNull(result);
		assertSame(expectedDto, result);
		verify(sloganRepositoryPort).createSlogan(domain);
		verify(mapper).toDomain(inputDto);
		verify(mapper).toDto(domain);
	}

	@Test
	void createSlogan_ThrowsException() {
		SloganDTO inputDto = new SloganDTO();
		when(mapper.toDomain(any(SloganDTO.class))).thenThrow(new RuntimeException("Test exception"));

		Exception exception = assertThrows(RuntimeException.class, () -> service.createSlogan(inputDto));

		assertEquals("Test exception", exception.getMessage());
		verify(mapper).toDomain(inputDto);
		verifyNoInteractions(sloganRepositoryPort);
	}
}

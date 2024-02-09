package com.mango.customer.application.service;

import com.mango.customer.application.mapper.SloganDtoMapper;
import com.mango.customer.application.model.SloganDTO;
import com.mango.customer.domain.model.SloganDomain;
import com.mango.customer.domain.port.SloganRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SloganService {

	private final SloganRepositoryPort sloganRepositoryPort;
	private final SloganDtoMapper mapper;

	public SloganDTO createSlogan(SloganDTO sloganDTO) {
		SloganDomain sloganDomain = sloganRepositoryPort.createSlogan(mapper.toDomain(sloganDTO));
		return mapper.toDto(sloganDomain);
	}
}

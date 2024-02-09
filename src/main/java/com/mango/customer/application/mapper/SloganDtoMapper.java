package com.mango.customer.application.mapper;

import com.mango.customer.application.model.SloganDTO;
import com.mango.customer.domain.model.SloganDomain;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SloganDtoMapper {
	SloganDomain toDomain(SloganDTO sloganDTO);
	SloganDTO toDto(SloganDomain sloganDomain);
}

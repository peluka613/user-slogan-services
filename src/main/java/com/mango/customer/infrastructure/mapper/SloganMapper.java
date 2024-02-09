package com.mango.customer.infrastructure.mapper;

import com.mango.customer.domain.model.SloganDomain;
import com.mango.customer.infrastructure.model.SloganEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SloganMapper {
	@Mapping(target = "email", source = "sloganEntity.userEntity.email")
	SloganDomain toDomain(SloganEntity sloganEntity);

	SloganEntity toEntity(SloganDomain sloganDomain);
}

package com.mango.customer.infrastructure.mapper;

import com.mango.customer.domain.model.UserDomain;
import com.mango.customer.infrastructure.model.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
	UserDomain toDomain(UserEntity userEntity);
	UserEntity toEntity(UserDomain userDomain);
}

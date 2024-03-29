package com.mango.customer.application.mapper;

import com.mango.customer.application.model.UserDTO;
import com.mango.customer.domain.model.UserDomain;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {
	UserDomain toDomain(UserDTO userDTO);
	UserDTO toDto(UserDomain userDomain);
}

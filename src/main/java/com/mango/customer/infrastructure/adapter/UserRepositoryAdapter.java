package com.mango.customer.infrastructure.adapter;

import com.mango.customer.domain.model.UserDomain;
import com.mango.customer.domain.port.UserRepositoryPort;
import com.mango.customer.infrastructure.exception.UserRegisterException;
import com.mango.customer.infrastructure.mapper.UserMapper;
import com.mango.customer.infrastructure.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserJpaRepository jpaRepository;
    private final UserMapper mapper;

	@Override
	public UserDomain createUser(UserDomain domain) {
		Optional<UserEntity> userEntity = Optional.of(jpaRepository.save(mapper.toEntity(domain)));
		return mapper.toDomain(userEntity.orElseThrow(() -> new UserRegisterException(domain.getEmail())));
	}
}

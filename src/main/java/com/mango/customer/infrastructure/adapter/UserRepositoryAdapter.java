package com.mango.customer.infrastructure.adapter;

import com.mango.customer.domain.model.UserDomain;
import com.mango.customer.domain.port.UserRepositoryPort;
import com.mango.customer.infrastructure.exception.UnauthorizedUserModificationException;
import com.mango.customer.infrastructure.exception.UserNotFoundException;
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
	public UserDomain createUser(UserDomain user) {
		Optional<UserEntity> userEntity = Optional.ofNullable(jpaRepository.save(mapper.toEntity(user)));
		return mapper.toDomain(userEntity.orElseThrow(() -> new UserRegisterException(user.getEmail())));
	}

	@Override
	public UserDomain updateUser(UserDomain user, String email) {
		if (!email.equalsIgnoreCase(user.getEmail())) {
			throw new UnauthorizedUserModificationException(email);
		}
		Optional<UserEntity> userEntity = Optional.ofNullable(jpaRepository.findByEmail(email));
		Long userId = userEntity.map(UserEntity::getId).orElseThrow(() -> new UserNotFoundException(email));

		UserEntity mappedUserEntity = mapper.toEntity(user);
		mappedUserEntity.setId(userId);

		userEntity = Optional.of(jpaRepository.save(mappedUserEntity));
		return mapper.toDomain(userEntity.orElseThrow(() -> new UserRegisterException(user.getEmail())));
	}

	@Override
	public UserDomain findUserByEmail(String email) {
		Optional<UserEntity> userEntity = Optional.ofNullable(jpaRepository.findByEmail(email));
		return mapper.toDomain(userEntity.orElseThrow(() -> new UserNotFoundException(email)));
	}
}

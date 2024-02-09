package com.mango.customer.infrastructure.adapter;

import com.mango.customer.domain.model.SloganDomain;
import com.mango.customer.domain.port.SloganRepositoryPort;
import com.mango.customer.infrastructure.exception.MaxAllowedSlogansReachedException;
import com.mango.customer.infrastructure.exception.SloganRegisterException;
import com.mango.customer.infrastructure.exception.UserNotFoundException;
import com.mango.customer.infrastructure.mapper.SloganMapper;
import com.mango.customer.infrastructure.model.SloganEntity;
import com.mango.customer.infrastructure.model.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class SloganRepositoryAdapter implements SloganRepositoryPort {

	private final SloganJpaRepository jpaRepository;
	private final UserJpaRepository userJpaRepository;
	private final SloganMapper mapper;

	private final Integer maxSlogansAllowed;
	public SloganRepositoryAdapter(SloganJpaRepository jpaRepository,
								   UserJpaRepository userJpaRepository,
								   SloganMapper mapper,
								   @Value("${user.slogans.max_allowed}") Integer maxSlogansAllowed) {
		this.jpaRepository = jpaRepository;
		this.userJpaRepository = userJpaRepository;
		this.mapper = mapper;
		this.maxSlogansAllowed = maxSlogansAllowed;
	}
	@Override
	public SloganDomain createSlogan(SloganDomain domain) {

		Optional<UserEntity> userEntity = Optional.ofNullable(userJpaRepository.findByEmail(domain.getEmail()));
		Long userId = userEntity.map(UserEntity::getId).orElseThrow(() -> new UserNotFoundException(domain.getEmail()));

		int totalSlogansByUserId = jpaRepository.countByUserEntityId(userId);

		if (totalSlogansByUserId >= maxSlogansAllowed){
			throw new MaxAllowedSlogansReachedException(domain.getEmail(), maxSlogansAllowed);
		}

		SloganEntity mappedSloganEntity = mapper.toEntity(domain);
		userEntity.ifPresent(mappedSloganEntity::setUserEntity);
		Optional<SloganEntity> sloganEntity = Optional.ofNullable(jpaRepository.save(mappedSloganEntity));
		return mapper.toDomain(sloganEntity.orElseThrow(() -> new SloganRegisterException(domain.getEmail())));
	}
}

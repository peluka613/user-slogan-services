package com.mango.customer.service;

import com.mango.customer.infrastructure.adapter.UserJpaRepository;
import com.mango.customer.infrastructure.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserJpaRepository userJpaRepository;

	public UserEntity saveUser(UserEntity userEntity) {
		return userJpaRepository.save(userEntity);
	}

	public UserEntity getUserById(Long id) {
		return userJpaRepository.findById(id).orElse(null);
	}
}

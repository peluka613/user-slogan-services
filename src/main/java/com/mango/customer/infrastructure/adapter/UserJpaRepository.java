package com.mango.customer.infrastructure.adapter;

import com.mango.customer.infrastructure.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
	UserEntity findByEmail(String email);
}

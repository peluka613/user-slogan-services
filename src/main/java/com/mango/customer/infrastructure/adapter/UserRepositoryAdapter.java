package com.mango.customer.infrastructure.adapter;

import com.mango.customer.domain.port.UserRepositoryPort;
import com.mango.customer.infrastructure.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserJpaRepository jpaRepository;
    private final UserMapper mapper;

}

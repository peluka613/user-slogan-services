package com.mango.customer.domain.port;

import com.mango.customer.domain.model.UserDomain;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoryPort {
	UserDomain createUser(UserDomain domain);

	UserDomain updateUser(UserDomain domain, String email);

	UserDomain findUserByEmail(String email);
}

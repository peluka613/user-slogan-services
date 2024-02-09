package com.mango.customer.domain.port;

import com.mango.customer.domain.model.SloganDomain;
import org.springframework.stereotype.Repository;

@Repository
public interface SloganRepositoryPort {
	SloganDomain createSlogan(SloganDomain domain);

}

package com.mango.customer.infrastructure.adapter;

import com.mango.customer.infrastructure.model.SloganEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SloganJpaRepository extends JpaRepository<SloganEntity, Long> {
	Integer countByUserEntityId(Long userId);
}

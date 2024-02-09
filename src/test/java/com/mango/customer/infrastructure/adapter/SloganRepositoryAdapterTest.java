package com.mango.customer.infrastructure.adapter;

import com.mango.customer.domain.model.SloganDomain;
import com.mango.customer.infrastructure.exception.MaxAllowedSlogansReachedException;
import com.mango.customer.infrastructure.exception.SloganRegisterException;
import com.mango.customer.infrastructure.exception.UserNotFoundException;
import com.mango.customer.infrastructure.mapper.SloganMapper;
import com.mango.customer.infrastructure.model.SloganEntity;
import com.mango.customer.infrastructure.model.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource("classpath:application.yml")
class SloganRepositoryAdapterTest {

	@Mock
	private SloganJpaRepository jpaRepository;

	@Mock
	private UserJpaRepository userJpaRepository;

	@Mock
	private SloganMapper mapper;

	private SloganRepositoryAdapter adapter;

	@BeforeEach
	void setUp() {
		adapter = new SloganRepositoryAdapter(jpaRepository, userJpaRepository,
			mapper, 3);
	}

	@Test
	void createSlogan_Success() {
		SloganEntity sloganEntity = getSloganEntity();
		SloganDomain sloganDomain = getSloganDomain();
		when(userJpaRepository.findByEmail(sloganEntity.getUserEntity().getEmail())).thenReturn(sloganEntity.getUserEntity());
		when(jpaRepository.countByUserEntityId(anyLong())).thenReturn(0);
		when(mapper.toEntity(any(SloganDomain.class))).thenReturn(sloganEntity);
		when(jpaRepository.save(any(SloganEntity.class))).thenReturn(sloganEntity);
		when(mapper.toDomain(any(SloganEntity.class))).thenReturn(sloganDomain);

		assertDoesNotThrow(() -> adapter.createSlogan(sloganDomain));
	}

	private static UserEntity getUserEntity() {
		UserEntity newUser = new UserEntity();
		newUser.setId(1L);
		newUser.setName("John");
		newUser.setLastname("Doe");
		newUser.setEmail("john.doe@example.com");
		newUser.setAddress("123 Main St");
		newUser.setCity("Anytown");
		return newUser;
	}

	private static SloganEntity getSloganEntity() {
		SloganEntity sloganEntity = new SloganEntity();
		sloganEntity.setUserEntity(getUserEntity());
		sloganEntity.setSlogan("Slogan test");
		return sloganEntity;
	}

	private static SloganDomain getSloganDomain() {
		SloganDomain sloganDomain = new SloganDomain();
		sloganDomain.setEmail("john.doe@example.com");
		sloganDomain.setSlogan("Slogan test");
		return sloganDomain;
	}

	@Test
	void createSlogan_UserNotFound() {
		when(userJpaRepository.findByEmail(anyString())).thenReturn(null);

		SloganDomain sloganDomain = getSloganDomain();
		assertThrows(UserNotFoundException.class, () -> adapter.createSlogan(sloganDomain));
	}

	@Test
	void createSlogan_MaxSlogansReached() {
		when(userJpaRepository.findByEmail(anyString())).thenReturn(getUserEntity());
		when(jpaRepository.countByUserEntityId(anyLong())).thenReturn(10);

		SloganDomain sloganDomain = getSloganDomain();
		assertThrows(MaxAllowedSlogansReachedException.class, () -> adapter.createSlogan(sloganDomain));
	}

	@Test
	void createSlogan_SloganRegisterException() {
		when(userJpaRepository.findByEmail(anyString())).thenReturn(getUserEntity());
		when(jpaRepository.countByUserEntityId(anyLong())).thenReturn(0);
		when(mapper.toEntity(any(SloganDomain.class))).thenReturn(getSloganEntity());
		when(jpaRepository.save(any(SloganEntity.class))).thenReturn(null);

		SloganDomain sloganDomain = getSloganDomain();
		assertThrows(SloganRegisterException.class, () -> adapter.createSlogan(sloganDomain));
	}
}

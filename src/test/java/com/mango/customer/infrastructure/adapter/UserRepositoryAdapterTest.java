package com.mango.customer.infrastructure.adapter;

import com.mango.customer.domain.model.UserDomain;
import com.mango.customer.infrastructure.exception.UnauthorizedUserModificationException;
import com.mango.customer.infrastructure.exception.UserNotFoundException;
import com.mango.customer.infrastructure.exception.UserRegisterException;
import com.mango.customer.infrastructure.mapper.UserMapper;
import com.mango.customer.infrastructure.model.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRepositoryAdapterTest {

    @Mock
    private UserJpaRepository jpaRepository;

    @Mock
    private UserMapper mapper;

    @InjectMocks
    private UserRepositoryAdapter userRepositoryAdapter;

    @Test
    void createUser_Success() {
        UserDomain userDomain = new UserDomain();
        UserEntity userEntity = new UserEntity();
        when(mapper.toEntity(any(UserDomain.class))).thenReturn(userEntity);
        when(jpaRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        when(mapper.toDomain(any(UserEntity.class))).thenReturn(userDomain);

        UserDomain result = userRepositoryAdapter.createUser(userDomain);

        assertNotNull(result);
        verify(jpaRepository).save(userEntity);
        verify(mapper).toDomain(userEntity);
    }

    @Test
    void createUser_Failure() {
        UserDomain userDomain = new UserDomain();
        when(mapper.toEntity(any(UserDomain.class))).thenReturn(new UserEntity());
        when(jpaRepository.save(any(UserEntity.class))).thenThrow(new UserRegisterException("Database error"));

        assertThrows(UserRegisterException.class, () -> userRepositoryAdapter.createUser(userDomain));
    }

    @Test
    void updateUser_Success() {
        String email = "user@example.com";
        UserDomain userDomain = new UserDomain();
        userDomain.setEmail(email);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setEmail(email);

        when(jpaRepository.findByEmail(email)).thenReturn(userEntity);
        when(mapper.toEntity(any(UserDomain.class))).thenReturn(userEntity);
        when(jpaRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        when(mapper.toDomain(any(UserEntity.class))).thenReturn(userDomain);

        UserDomain result = userRepositoryAdapter.updateUser(userDomain, email);

        assertNotNull(result);
        verify(jpaRepository).save(userEntity);
        verify(mapper).toDomain(userEntity);
    }

    @Test
    void updateUser_UnauthorizedModification() {
        String email = "user@example.com";
        UserDomain userDomain = new UserDomain();
        userDomain.setEmail("other@example.com");

        assertThrows(UnauthorizedUserModificationException.class, () -> userRepositoryAdapter.updateUser(userDomain, email));
    }

    @Test
    void findUserByEmail_Success() {
        String email = "user@example.com";
        UserEntity userEntity = new UserEntity();
        UserDomain expectedUserDomain = new UserDomain();

        when(jpaRepository.findByEmail(email)).thenReturn(userEntity);
        when(mapper.toDomain(userEntity)).thenReturn(expectedUserDomain);

        UserDomain result = userRepositoryAdapter.findUserByEmail(email);

        assertNotNull(result);
        assertEquals(expectedUserDomain, result);
        verify(jpaRepository).findByEmail(email);
        verify(mapper).toDomain(userEntity);
    }

    @Test
    void findUserByEmail_NotFound() {
        String email = "nonexistent@example.com";
        when(jpaRepository.findByEmail(email)).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> userRepositoryAdapter.findUserByEmail(email));
    }
}

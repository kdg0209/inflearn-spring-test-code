package com.inflearn.testcode.dao;

import com.inflearn.testcode.exception.ResourceNotFoundException;
import com.inflearn.testcode.model.UserStatus;
import com.inflearn.testcode.repository.UserEntity;
import com.inflearn.testcode.repository.UserRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.inflearn.testcode.repository.QUserEntity.userEntity;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserDao {

    private final JPAQueryFactory queryFactory;
    private final UserRepository userRepository;

    public UserEntity findById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Users", id));
    }

    public UserEntity findByIdAndStatus(long id, UserStatus status) {
        UserEntity result = queryFactory
                .selectFrom(userEntity)
                .where(
                        userEntity.id.eq(id),
                        userEntity.status.eq(status)
                )
                .fetchFirst();

        return Optional.ofNullable(result)
                .orElseThrow(() -> new ResourceNotFoundException("Users", id));
    }

    public UserEntity findByEmailAndStatus(String email, UserStatus status) {
        UserEntity result = queryFactory
                .selectFrom(userEntity)
                .where(
                        userEntity.email.eq(email),
                        userEntity.status.eq(status)
                )
                .fetchFirst();

        return Optional.ofNullable(result)
                .orElseThrow(() -> new ResourceNotFoundException("Users", email));
    }

    public UserEntity getById(long id) {
        UserEntity result = queryFactory
                .selectFrom(userEntity)
                .where(
                        userEntity.id.eq(id),
                        userEntity.status.eq(UserStatus.ACTIVE)
                )
                .fetchFirst();

        return Optional.ofNullable(result)
                .orElseThrow(() -> new ResourceNotFoundException("Users", id));
    }

    public UserEntity getByEmail(String email) {
        UserEntity result = queryFactory
                .selectFrom(userEntity)
                .where(
                        userEntity.email.eq(email),
                        userEntity.status.eq(UserStatus.ACTIVE)
                )
                .fetchFirst();

        return Optional.ofNullable(result)
                .orElseThrow(() -> new ResourceNotFoundException("Users", email));
    }
}

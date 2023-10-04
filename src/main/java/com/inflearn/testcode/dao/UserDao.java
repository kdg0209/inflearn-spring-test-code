package com.inflearn.testcode.dao;

import com.inflearn.testcode.model.UserStatus;
import com.inflearn.testcode.repository.UserEntity;
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

    public Optional<UserEntity> findByIdAndStatus(long id, UserStatus status) {
        UserEntity result = queryFactory
                .selectFrom(userEntity)
                .where(
                        userEntity.id.eq(id),
                        userEntity.status.eq(status)
                )
                .fetchFirst();

        return Optional.ofNullable(result);
    }

    public Optional<UserEntity> findByEmailAndStatus(String email, UserStatus status) {
        UserEntity result = queryFactory
                .selectFrom(userEntity)
                .where(
                        userEntity.email.eq(email),
                        userEntity.status.eq(status)
                )
                .fetchFirst();

        return Optional.ofNullable(result);
    }
}

package com.inflearn.testcode.dao;

import com.inflearn.testcode.model.UserStatus;
import com.inflearn.testcode.repository.UserEntity;
import com.inflearn.testcode.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql("/sql/user-repository-test-data.sql")
class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    void USER의_아이디와_상태를_사용하여_USER를_찾을_수_있다() {

        // given
        long id = 1L;
        UserStatus status = UserStatus.ACTIVE;

        // when
        UserEntity result = userDao.findByIdAndStatus(id, status);

        // then
        assertThat(result).isNotNull();
    }

    @Test
    void USER의_아이디와_상태를_사용하여_USER를_찾을_수_없다면_빈_객체를_반환한다() {

        // given
        long id = 2L;
        UserStatus status = UserStatus.ACTIVE;

        // when
        UserEntity result = userDao.findByIdAndStatus(id, status);

        // then
        assertThat(result).isNull();
    }

    @Test
    void USER의_이메일과_상태를_사용하여_USER를_찾을_수_있다() {

        // given
        String email = "test@naver.com";
        UserStatus status = UserStatus.ACTIVE;

        // when
        UserEntity result = userDao.findByEmailAndStatus(email, status);

        // then
        assertThat(result).isNotNull();
    }

    @Test
    void USER의_이메일과_상태를_사용하여_USER를_찾을_수_없다면_빈_객체를_반환한다() {

        // given
        String email = "test111@naver.com";
        UserStatus status = UserStatus.ACTIVE;

        // when
        UserEntity result = userDao.findByEmailAndStatus(email, status);

        // then
        assertThat(result).isNull();
    }
}
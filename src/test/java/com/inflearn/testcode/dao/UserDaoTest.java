package com.inflearn.testcode.dao;

import com.inflearn.testcode.exception.ResourceNotFoundException;
import com.inflearn.testcode.model.UserStatus;
import com.inflearn.testcode.repository.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@SqlGroup({
        @Sql(value = "/sql/user-repository-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-user-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @MockBean
    private JavaMailSender javaMailSender;

    @Test
    void  USER의_아이디와_상태를_사용하여_USER를_찾을_수_있다() {

        // given
        long id = 1L;
        UserStatus status = UserStatus.ACTIVE;

        // when
        UserEntity result = userDao.findByIdAndStatus(id, status);

        // then
        assertThat(result).isNotNull();
    }

    @Test
    void USER의_아이디와_상태를_사용하여_USER를_찾을_수_없다면_예외가_발생한다() {

        // given
        long id = 100L;
        UserStatus status = UserStatus.ACTIVE;

        // when && then
        assertThatThrownBy(() -> userDao.findByIdAndStatus(id, status))
                .isInstanceOf(ResourceNotFoundException.class);
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
    void USER의_이메일과_상태를_사용하여_USER를_찾을_수_없다면_예외를_발생한다() {

        // given
        String email = "test111@naver.com";
        UserStatus status = UserStatus.ACTIVE;

        // when && then
        assertThatThrownBy(() -> userDao.findByEmailAndStatus(email, status))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByEmail_메서드는_ACTIVE_상태인_사용자를_이메일을_사용하여_찾을_수_있다() {

        // given
        String email = "test@naver.com";

        // when
        UserEntity result = userDao.getByEmail(email);

        // then
        assertThat(result.getNickname()).isEqualTo("test");
    }

    @Test
    void getByEmail_메서드는_PENDING_상태인_사용자를_찾을_수_없다() {

        // given
        String email = "test1235555@naver.com";

        // when && then
        assertThatThrownBy(() -> userDao.getByEmail(email))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
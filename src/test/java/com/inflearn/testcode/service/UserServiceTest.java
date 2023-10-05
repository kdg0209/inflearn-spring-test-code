package com.inflearn.testcode.service;

import com.inflearn.testcode.dao.UserDao;
import com.inflearn.testcode.exception.CertificationCodeNotMatchedException;
import com.inflearn.testcode.model.UserStatus;
import com.inflearn.testcode.model.dto.UserCreateDto;
import com.inflearn.testcode.model.dto.UserUpdateDto;
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
        @Sql(value = "/sql/user-insert-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-user-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @MockBean
    private JavaMailSender javaMailSender;

    @Test
    void UserCreateDto_객체를_사용하여_유저를_생성할_수_있다() {

        // given
        UserCreateDto userCreateDto = new UserCreateDto("test@naver.com", "test", "Seoul");

        // when
        UserEntity result = userService.create(userCreateDto);

        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getStatus()).isEqualTo(UserStatus.PENDING);
//        assertThat(result.getCertificationCode()).isEqualTo("??") // uuid여서 현재 테스트 불가 FIXME

    }

    @Test
    void UserUpdateDto_객체를_사용하여_유저를_업데이트할_수_있다() {

        // given
        long id = 1L;
        UserUpdateDto userUpdateDto = new UserUpdateDto("홍길동", "제주도");

        // when
        UserEntity result = userService.update(id, userUpdateDto);

        // then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getNickname()).isEqualTo("홍길동");
        assertThat(result.getAddress()).isEqualTo("제주도");
    }

    @Test
    void login_메서드를_사용하여_사용자의_마지막_로그인_시간을_업데이트_할_수_있다() {

        // given && when
        userService.login(1L);

        // then
        UserEntity result = userDao.findById(1L);
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getLastLoginAt()).isGreaterThan(0);
//        assertThat(result.getLastLoginAt()).isEqualTo("???"); FIXME
    }

    @Test
    void verifyEmail_메서드를_사용하여_사용자는_인증코드를_사용하여_상태를_ACTIVE로_업데이트_할_수_있다() {

        // given
        long id = 3L;
        String certificationCode = "qwer";

        // when
        userService.verifyEmail(id, certificationCode);

        // then
        UserEntity result = userDao.findById(id);
        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void verifyEmail_메서드를_사용하여_사용자는_잘못된_인증코드를_사용한다면_예외가_발생한다() {

        // given
        long id = 3L;
        String certificationCode = "qwertyuiop";

        // when && then
        assertThatThrownBy(() -> userService.verifyEmail(id, certificationCode))
                .isInstanceOf(CertificationCodeNotMatchedException.class);
    }
}
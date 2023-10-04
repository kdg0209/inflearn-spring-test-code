package com.inflearn.testcode.repository;

import com.inflearn.testcode.model.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource("classpath:application.yml")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void UserRepository가_정상적으로_연결되었다() {

        // given
        UserEntity userEntity = UserEntity.builder()
                .email("test@naver.com")
                .address("Seoul")
                .nickName("test")
                .status(UserStatus.ACTIVE)
                .certificationCode("qwer")
                .build();

        // when
        UserEntity result = userRepository.save(userEntity);

        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getEmail()).isEqualTo("test@naver.com");
        assertThat(result.getAddress()).isEqualTo("Seoul");
        assertThat(result.getNickName()).isEqualTo("test");
        assertThat(result.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(result.getCertificationCode()).isEqualTo("qwer");
    }

}
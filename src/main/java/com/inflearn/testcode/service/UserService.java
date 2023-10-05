package com.inflearn.testcode.service;

import com.inflearn.testcode.dao.UserDao;
import com.inflearn.testcode.exception.CertificationCodeNotMatchedException;
import com.inflearn.testcode.model.UserStatus;
import com.inflearn.testcode.model.dto.UserCreateDto;
import com.inflearn.testcode.model.dto.UserUpdateDto;
import com.inflearn.testcode.repository.UserEntity;
import com.inflearn.testcode.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;

    public UserEntity create(UserCreateDto userCreateDto) {
        UserEntity userEntity = UserEntity.builder()
                .email(userCreateDto.getEmail())
                .nickname(userCreateDto.getNickname())
                .address(userCreateDto.getAddress())
                .status(UserStatus.PENDING)
                .certificationCode(UUID.randomUUID().toString())
                .build();

        userEntity = userRepository.save(userEntity);
        String certificationUrl = generateCertificationUrl(userEntity);
        sendCertificationEmail(userCreateDto.getEmail(), certificationUrl);
        return userEntity;
    }

    public UserEntity update(long id, UserUpdateDto userUpdateDto) {
        UserEntity userEntity = userDao.getById(id);

        UserEntity newUserEntity = UserEntity.builder()
                .id(userEntity.getId())
                .email(userEntity.getEmail())
                .nickname(userUpdateDto.getNickname())
                .address(userUpdateDto.getAddress())
                .status(userEntity.getStatus())
                .certificationCode(UUID.randomUUID().toString())
                .build();

        userEntity = userRepository.save(userEntity);
        return userEntity;
    }

    public void login(long id) {
        UserEntity userEntity = userDao.findById(id);
        userEntity.setLastLoginAt(Clock.systemUTC().millis());
    }

    public void verifyEmail(long id, String certificationCode) {
        UserEntity userEntity = userDao.findById(id);
        if (!certificationCode.equals(userEntity.getCertificationCode())) {
            throw new CertificationCodeNotMatchedException();
        }
        userEntity.setStatus(UserStatus.ACTIVE);
    }

    private void sendCertificationEmail(String email, String certificationUrl) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Please certify your email address");
        message.setText("Please click the following link to certify your email address: " + certificationUrl);
        mailSender.send(message);
    }

    private String generateCertificationUrl(UserEntity userEntity) {
        return "http://localhost:8080/api/users/" + userEntity.getId() + "/verify?certificationCode=" + userEntity.getCertificationCode();
    }
}

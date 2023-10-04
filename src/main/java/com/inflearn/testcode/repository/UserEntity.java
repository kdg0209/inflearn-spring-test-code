package com.inflearn.testcode.repository;

import com.inflearn.testcode.model.UserStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String address;
    private String nickName;

    @Enumerated(EnumType.STRING)
    private UserStatus status;
    private String certificationCode;

    @Builder
    public UserEntity(Long id, String email, String address, String nickName, UserStatus status, String certificationCode) {
        this.id = id;
        this.email = email;
        this.address = address;
        this.nickName = nickName;
        this.status = status;
        this.certificationCode = certificationCode;
    }
}

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

    @Column(name = "email")
    private String email;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "address")
    private String address;

    @Column(name = "certification_code")
    private String certificationCode;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(name = "last_login_at")
    private Long lastLoginAt;

    @Builder
    public UserEntity(Long id, String email, String address, String nickname, UserStatus status, String certificationCode) {
        this.id = id;
        this.email = email;
        this.address = address;
        this.nickname = nickname;
        this.status = status;
        this.certificationCode = certificationCode;
    }

    public void setLastLoginAt(Long lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }
}

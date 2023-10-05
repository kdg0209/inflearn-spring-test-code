package com.inflearn.testcode.model.dto;

import lombok.Getter;

@Getter
public class UserCreateDto {

    private final String email;
    private final String nickname;
    private final String address;

    public UserCreateDto(String email, String nickname, String address) {
        this.email = email;
        this.nickname = nickname;
        this.address = address;
    }
}

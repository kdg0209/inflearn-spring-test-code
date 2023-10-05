package com.inflearn.testcode.model.dto;

import lombok.Getter;

@Getter
public class UserUpdateDto {

    private final String nickname;
    private final String address;

    public UserUpdateDto(String nickname, String address) {
        this.nickname = nickname;
        this.address = address;
    }
}

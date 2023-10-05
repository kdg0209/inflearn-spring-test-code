package com.inflearn.testcode.model.dto;

import lombok.Getter;

@Getter
public class PostUpdateDto {

    private final String content;

    public PostUpdateDto(String content) {
        this.content = content;
    }
}

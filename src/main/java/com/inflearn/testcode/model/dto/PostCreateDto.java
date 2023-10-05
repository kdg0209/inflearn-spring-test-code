package com.inflearn.testcode.model.dto;

import lombok.Getter;

@Getter
public class PostCreateDto {

    private final long writerId;
    private final String content;

    public PostCreateDto(long writerId, String content) {
        this.writerId = writerId;
        this.content = content;
    }
}

package com.inflearn.testcode.service;

import com.inflearn.testcode.model.dto.PostCreateDto;
import com.inflearn.testcode.model.dto.PostUpdateDto;
import com.inflearn.testcode.repository.PostEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@SqlGroup({
        @Sql(value = "/sql/user-insert-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-user-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
        @Sql(value = "/sql/posts-insert-data.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-posts-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class PostServiceTest {

    @Autowired
    private PostService postService;

    @MockBean
    private JavaMailSender javaMailSender;

    @Test
    void PostCreateDto_객체를_사용하여_게시글을_생성할_수_있다() {

        // given
        PostCreateDto postCreateDto = new PostCreateDto(1L, "content");

        // when
        PostEntity result = postService.create(postCreateDto);

        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getContent()).isEqualTo("content");
        assertThat(result.getWriter().getId()).isEqualTo(1L);
    }

    @Test
    void PostUpdateDto_객체를_사용하여_게시글을_업데이트할_수_있다() {

        // given
        long id = 1L;
        PostUpdateDto postUpdateDto = new PostUpdateDto("content update!!");

        // when
        PostEntity result = postService.update(id, postUpdateDto);

        // then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getContent()).isEqualTo("content update!!");
    }
}
package com.inflearn.testcode.service;

import com.inflearn.testcode.dao.PostDao;
import com.inflearn.testcode.dao.UserDao;
import com.inflearn.testcode.model.dto.PostCreateDto;
import com.inflearn.testcode.model.dto.PostUpdateDto;
import com.inflearn.testcode.repository.PostEntity;
import com.inflearn.testcode.repository.PostRepository;
import com.inflearn.testcode.repository.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final UserDao userDao;
    private final PostDao postDao;
    private final PostRepository postRepository;

    public PostEntity create(PostCreateDto postCreateDto) {
        UserEntity userEntity = userDao.getById(postCreateDto.getWriterId());
        PostEntity postEntity = new PostEntity();
        postEntity.setWriter(userEntity);
        postEntity.setContent(postCreateDto.getContent());
        postEntity.setCreatedAt(Clock.systemUTC().millis());

        return postRepository.save(postEntity);
    }

    public PostEntity update(long id, PostUpdateDto postUpdateDto) {
        PostEntity postEntity = postDao.getById(id);
        postEntity.setContent(postUpdateDto.getContent());
        postEntity.setModifiedAt(Clock.systemUTC().millis());
        return postRepository.save(postEntity);
    }
}

package com.inflearn.testcode.service;

import com.inflearn.testcode.dao.UserDao;
import com.inflearn.testcode.exception.ResourceNotFoundException;
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
    private final UserService userService;
    private final PostRepository postRepository;

    public PostEntity getPostById(long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Posts", id));
    }

    public PostEntity createPost(PostCreateDto postCreateDto) {
        UserEntity userEntity = userDao.getByIdOrElseThrow(postCreateDto.getWriterId());
        PostEntity postEntity = new PostEntity();
        postEntity.setWriter(userEntity);
        postEntity.setContent(postCreateDto.getContent());
        postEntity.setCreatedAt(Clock.systemUTC().millis());

        return postRepository.save(postEntity);
    }

    public PostEntity updatePost(long id, PostUpdateDto postUpdateDto) {
        PostEntity postEntity = getPostById(id);
        postEntity.setContent(postUpdateDto.getContent());
        postEntity.setModifiedAt(Clock.systemUTC().millis());
        return postRepository.save(postEntity);
    }
}

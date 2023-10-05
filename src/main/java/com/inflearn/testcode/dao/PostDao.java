package com.inflearn.testcode.dao;

import com.inflearn.testcode.exception.ResourceNotFoundException;
import com.inflearn.testcode.repository.PostEntity;
import com.inflearn.testcode.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostDao {

    private final PostRepository postRepository;

    public PostEntity getById(long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Posts", id));
    }
}

package com.example.demo.service;

import com.example.demo.request.PostRequest;
import com.example.demo.response.PostResponse;
import com.example.demo.response.list.PostResponseList;

public interface PostService {
    void createUser(PostRequest request);

    PostResponse getBYId(Long postId);

    PostResponseList getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    void updatePost(Long postId, PostRequest request);

    void deletePost(Long postId);
}

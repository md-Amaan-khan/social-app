package com.example.demo.serviceImpl;

import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.request.PostRequest;
import com.example.demo.response.PostResponse;
import com.example.demo.response.list.PostResponseList;
import com.example.demo.service.PostService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void createUser(PostRequest request) {
        Post post=new Post();
        post.setTitle(request.getTitle());
        post.setContent((request.getContent()));
        post.setUser(checkUser(request.getAuthorId()));
        post.setCreatedAt(request.getCreatedAt());
        post.setUpdatedAt(request.getUpdatedAt());
        postRepository.save(post);
    }

    public User checkUser(@NotBlank Long authorId) {
        User user = userRepository.findById(authorId).orElseThrow(()-> new RuntimeException("user not found"));
        return user;
    }

    @Override
    public PostResponse getBYId(Long postId) {
        Post post=postRepository.findById(postId).orElseThrow(()-> new RuntimeException("Post not Found"));
        return new PostResponse(post);
    }

    @Override
    public PostResponseList getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Pageable pageable= PageRequest.of(pageNumber, pageSize, sortDir.equalsIgnoreCase("asc")? Sort.Direction.ASC: Sort.Direction.DESC, sortBy);
        Page<Post> users = postRepository.findAll(pageable);
        List<PostResponse> list = users.stream().map(this::changeToResponsePost).toList();
        return new PostResponseList(pageNumber, pageSize, sortDir, sortBy, users.getTotalPages(), list);
    }

    @Override
    public void updatePost(Long postId, PostRequest request) {
        Post post=postRepository.findById(postId).orElseThrow(()->new RuntimeException("Post Not Found"));
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setUser(checkUser(request.getAuthorId()));
        post.setCreatedAt(request.getCreatedAt());
        post.setUpdatedAt(request.getUpdatedAt());
        postRepository.save(post);
    }

    @Override
    public void deletePost(Long postId) {

        Post post=postRepository.findById(postId).orElseThrow(()->new RuntimeException("Post not Found"));
        postRepository.delete(post);
    }
    public PostResponse changeToResponsePost(Post post) {
        PostResponse postResponse = new PostResponse();
        postResponse.setPostId(post.getPostId());
        postResponse.setTitle(post.getTitle());
        postResponse.setContent(post.getContent());
        postResponse.setAuthorId(post.getUser().getUserId());
        postResponse.setCreatedAt(post.getCreatedAt());
        postResponse.setUpdatedAt(post.getUpdatedAt());
        return postResponse;
    }
}

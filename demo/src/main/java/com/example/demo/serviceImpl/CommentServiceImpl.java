package com.example.demo.serviceImpl;

import com.example.demo.model.Comment;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.request.CommentRequest;
import com.example.demo.response.CommentResponse;
import com.example.demo.response.list.CommentResponseList;
import com.example.demo.service.CommentService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository repository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    @Override
    public void createComment(CommentRequest request) {
        Comment comment = new Comment();
        comment.setPost(postRepository.findById(request.getPostId()).orElseThrow(() -> new RuntimeException("post not found")));
        comment.setCreatedAt(request.getCreatedAt());
        comment.setContent(request.getContent());
        comment.setAuthor(checkUser(request.getAuthorId()));
        comment.setCreatedAt(request.getCreatedAt());
        repository.save(comment);
    }

    public User checkUser(@NotBlank Long authorId) {
        return userRepository.findById(authorId).orElseThrow(() -> new RuntimeException("user not found"));

    }

    @Override
    public CommentResponse getComment(Long commentId) {


        Comment comment = repository.findById(commentId).orElseThrow(() -> new RuntimeException("comment Not Found"));

        return new CommentResponse(comment);
    }

    @Override
    public CommentResponseList getAllComment(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortDir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        Page<Comment> users = repository.findAll(pageable);

        List<CommentResponse> list = users.stream().map(this::changeTooResponse).toList();

        return new CommentResponseList(pageNumber, pageSize, sortDir, sortBy, users.getTotalPages(), list);
    }

    @Override
    public void updateComment(Long commentId, CommentRequest request) {
        Comment comment = repository.findById(commentId).orElseThrow(() -> new RuntimeException("Comment Id Not Found"));
        comment.setPost(checkPost(request.getPostId()));
        comment.setContent(request.getContent());
        comment.setAuthor(checkUser(request.getAuthorId()));
        comment.setCreatedAt(request.getCreatedAt());
        repository.save(comment);
    }

    public Post checkPost(@NotBlank Long authorId) {
        return postRepository.findById(authorId).orElseThrow(() -> new RuntimeException("user not found"));

    }

    @Override
    public void deleteComment(Long commentId) {
        Comment comment = repository.findById(commentId).orElseThrow(() -> new RuntimeException("Comment Not Found"));
        repository.delete(comment);
    }

    public CommentResponse changeTooResponse(Comment comment) {
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setCommentId(comment.getCommentId());
        commentResponse.setContent(comment.getContent());
        commentResponse.setPostId(comment.getPost().getPostId());
        commentResponse.setAuthorId(comment.getAuthor().getUserId());
        commentResponse.setCreatedAt(comment.getCreatedAt());
        return commentResponse;
    }
}

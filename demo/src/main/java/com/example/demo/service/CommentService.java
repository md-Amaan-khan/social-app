package com.example.demo.service;

import com.example.demo.request.CommentRequest;
import com.example.demo.response.CommentResponse;
import com.example.demo.response.MessageResponse;
import com.example.demo.response.list.CommentResponseList;
import org.springframework.stereotype.Service;
@Service
public interface CommentService {

     void createComment(CommentRequest request);

    CommentResponse getComment (Long commentId);

    CommentResponseList getAllComment(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    void updateComment(Long commentId, CommentRequest request);

    void deleteComment(Long commentId);


}

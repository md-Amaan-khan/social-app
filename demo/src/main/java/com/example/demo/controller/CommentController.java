package com.example.demo.controller;

import com.example.demo.request.CommentRequest;
import com.example.demo.response.CommentResponse;
import com.example.demo.response.MessageResponse;
import com.example.demo.response.list.CommentResponseList;
import com.example.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    public CommentService service;
    @PostMapping("/")
    public ResponseEntity<MessageResponse> createComment(@RequestBody CommentRequest request){
        service.createComment(request);
        return new ResponseEntity<>(new MessageResponse("Comment Created"), HttpStatus.OK);
    }
    @GetMapping("/{CommentId}")
    public ResponseEntity<?> getComment(@PathVariable Long CommentId){
        try {
            return new ResponseEntity<>(service.getComment(CommentId), HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    @GetMapping("/")
    public ResponseEntity<CommentResponseList> getAllComment(@RequestParam(defaultValue = "0", required = false) Integer pageNumber,
                                                             @RequestParam(defaultValue = "20", required = false) Integer pageSize,
                                                             @RequestParam(defaultValue = "commentId", required = false) String sortBy,
                                                             @RequestParam(defaultValue = "asc", required = false) String sortDir){
        return new ResponseEntity<>(service.getAllComment(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
    }
    @PutMapping("/{CommentId}")
    public ResponseEntity<MessageResponse> updateComment(@PathVariable Long CommentId, @RequestBody CommentRequest request){
        service.updateComment(CommentId, request);
        return new ResponseEntity<>(new MessageResponse("Comment Id not Found"), HttpStatus.OK);
    }
    @DeleteMapping("/{CommentId}")
    public ResponseEntity<MessageResponse> deleteComment(@PathVariable Long CommentId){
        service.deleteComment(CommentId);
    return new ResponseEntity<>(new MessageResponse("deleted successfully"),HttpStatus.OK);
    }

}

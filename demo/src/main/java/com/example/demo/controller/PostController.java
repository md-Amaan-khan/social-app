package com.example.demo.controller;

import com.example.demo.request.PostRequest;
import com.example.demo.response.util.MessageResponse;
import com.example.demo.response.PostResponse;
import com.example.demo.response.list.PostResponseList;
import com.example.demo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Post")
public class PostController {
    @Autowired
    private PostService service;
    @PostMapping("/")
    public ResponseEntity<MessageResponse> createUser(@RequestBody PostRequest request){
        service.createUser(request);
        return new ResponseEntity<>(new MessageResponse("post created"), HttpStatus.OK);
    }
    @GetMapping("/{PostId}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long PostId){

        return new ResponseEntity<>(service.getBYId(PostId),HttpStatus.OK);
    }
    @GetMapping("/")
    public ResponseEntity<PostResponseList> getAllPost(@RequestParam(defaultValue = "0", required = false) Integer pageNumber,
                                                       @RequestParam(defaultValue = "20", required = false) Integer pageSize,
                                                       @RequestParam(defaultValue = "postId", required = false) String sortBy,
                                                       @RequestParam(defaultValue = "asc", required = false) String sortDir){
        return new ResponseEntity<>(service.getAllPost(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
    }
    @PutMapping("/{PostId}")
    public ResponseEntity<MessageResponse> updatePost(@PathVariable Long PostId, @RequestBody PostRequest request){
        service.updatePost(PostId,request);
        return new ResponseEntity<>(new MessageResponse("Updated successfully"), HttpStatus.OK);
    }
    @DeleteMapping("/{PostId}")
    public ResponseEntity<MessageResponse> deletePost(@PathVariable Long PostId){
        service.deletePost(PostId);
        return new ResponseEntity<>(new MessageResponse("Deleted successfully"), HttpStatus.OK);
    }
}

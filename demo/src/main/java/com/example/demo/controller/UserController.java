package com.example.demo.controller;

import com.example.demo.request.UserRequest;
import com.example.demo.response.util.MessageResponse;
import com.example.demo.response.UserResponse;
import com.example.demo.response.list.UserResponseList;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/User")
public class UserController {
        @Autowired
        public UserService service;
@PostMapping("/")
public ResponseEntity<MessageResponse> createUser(@RequestBody UserRequest request){
service.createUser(request);
return new ResponseEntity<>(new MessageResponse("User Created"), HttpStatus.OK);
}
@GetMapping("/{UserId}")
    public ResponseEntity<UserResponse> getById(@PathVariable Long UserId){
return new ResponseEntity<>(service.getById(UserId), HttpStatus.OK);
}
@GetMapping("/")
    public ResponseEntity<UserResponseList> getAllId(@RequestParam(defaultValue = "0", required = false) Integer pageNumber,
                                                     @RequestParam(defaultValue = "20", required = false) Integer pageSize,
                                                     @RequestParam(defaultValue = "userId", required = false) String sortBy,
                                                     @RequestParam(defaultValue = "asc", required = false) String sortDir){
    return new ResponseEntity<>(service.getAllId(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
}
@PutMapping("/{UserId}")
    public ResponseEntity<MessageResponse> updateById(@PathVariable Long UserId , @RequestBody UserRequest request){
    service.updateById(UserId,request);
    return new ResponseEntity<>(new MessageResponse("Updated sucessfully"), HttpStatus.OK);
}
@DeleteMapping("/{UserId}")
    public ResponseEntity<MessageResponse> deleteById(@PathVariable Long UserId){

    service.deleteById(UserId);
return new ResponseEntity<>(new MessageResponse("Deleted sucessfully"), HttpStatus.OK);
}
}

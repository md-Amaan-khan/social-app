package com.example.demo.service;

import com.example.demo.request.UserRequest;
import com.example.demo.response.UserResponse;
import com.example.demo.response.list.UserResponseList;

public interface UserService {

    void createUser(UserRequest request);

    UserResponse getById(Long userId);

    UserResponseList getAllId(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    void updateById(Long userId, UserRequest request);

    void deleteById(Long userId);
}

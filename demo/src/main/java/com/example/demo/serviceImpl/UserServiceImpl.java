package com.example.demo.serviceImpl;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.request.UserRequest;
import com.example.demo.response.UserResponse;
import com.example.demo.response.list.UserResponseList;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public void createUser(UserRequest request) {
        User user=new User();
        user.setUserName(request.getUserName());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        userRepository.save(user);
    }

    @Override
    public UserResponse getById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not found"));
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(user.getUserId());
        userResponse.setUserName(user.getUserName());
        userResponse.setPassword(user.getPassword());
        userResponse.setEmail(user.getEmail());
        return userResponse;
    }

    @Override
    public UserResponseList getAllId(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Pageable pageable= PageRequest.of(pageNumber, pageSize, sortDir.equalsIgnoreCase("asc")? Sort.Direction.ASC: Sort.Direction.DESC, sortBy);
        Page<User> users = userRepository.findAll(pageable);
        List<UserResponse> list = users.stream().map(this::changeToResponse).toList();
        return new UserResponseList(pageNumber, pageSize, sortDir, sortBy, users.getTotalPages(), list);

    }

    @Override
    public void updateById(Long userId, UserRequest request) {
        User user=userRepository.findById(userId).orElseThrow(()-> new RuntimeException("user not found"));
        user.setEmail(request.getEmail());
        user.setUserName(request.getUserName());
        user.setPassword(request.getPassword());
        userRepository.save(user);
    }

    @Override
    public void deleteById(Long userId) {
        User user=userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not found"));
        userRepository.delete(user);
    }

    public UserResponse changeToResponse(User user){
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(user.getUserId());
        userResponse.setUserName(user.getUserName());
        userResponse.setPassword(user.getPassword());
        userResponse.setEmail(user.getEmail());
        return userResponse;
    }
}

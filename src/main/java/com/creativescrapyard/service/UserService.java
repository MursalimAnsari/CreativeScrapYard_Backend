package com.creativescrapyard.service;

import com.creativescrapyard.payload.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    // create user
    public UserDto createUser(UserDto user );

    //get user by username
    public UserDto getUserById(Long userId);

    //get all users
    public List<UserDto> getAllUsers();

    //delete user by id
    public void deleteUserById(Long userId);

    // update user
    UserDto updateUser(UserDto user, Long userId);

}

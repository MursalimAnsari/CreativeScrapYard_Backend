package com.creativescrapyard.service.impl;

import com.creativescrapyard.exceptions.ResourceNotFoundException;
import com.creativescrapyard.model.User;
import com.creativescrapyard.payload.UserDto;
import com.creativescrapyard.repo.UserRepository;
import com.creativescrapyard.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto)   {
         User user = this.dtoToUser(userDto);
         User savedUser = this.userRepository.save(user);
        return this.userToDto(savedUser);
    }


    @Override
    public UserDto updateUser(UserDto userDto, Long userId) {

        User user = this.userRepository.findById(userId)
                .orElseThrow(()->
                        new ResourceNotFoundException("user","id",userId));

        user.setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setProfilePic(userDto.getProfilePic());
        user.setPhone(userDto.getPhone());

        User updatedUser= this.userRepository.save(user);
        UserDto userDto1= userToDto(updatedUser);

        return userDto1;
    }


    @Override
    public UserDto getUserById(Long userId) {

        User user = this.userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User","Id", userId));

        return this.userToDto(user);
    }


    @Override
    public List<UserDto> getAllUsers() {

        List<User> users = this.userRepository.findAll();
        List<UserDto> userDtoList =
                users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());

        return userDtoList;
    }


    @Override
    public void deleteUserById(Long userId) {

        User user= this.userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User","Id", userId));

        this.userRepository.delete(user);
    }


    // model mapper alternative class
    public User  dtoToUser(UserDto userDto){

        User user =  this.modelMapper.map(userDto, User.class);

//        user.setId(userDto.getId());
//        user.setUsername(userDto.getUsername());
//        user.setFirstName(userDto.getFirstName());
//        user.setLastName(userDto.getLastName());
//        user.setPassword(userDto.getPassword());
//        user.setPhone(userDto.getPhone());
//        user.setEmail(userDto.getEmail());
//        user.setProfilePic(userDto.getProfilePic());

        return user;
    }

    // user to dto
    public  UserDto userToDto(User user){

        UserDto userDto= this.modelMapper.map(user,UserDto.class);

        return userDto;
    }


}


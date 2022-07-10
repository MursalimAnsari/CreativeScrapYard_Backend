package com.creativescrapyard.controller;

import com.creativescrapyard.payload.ApiResponse;
import com.creativescrapyard.payload.UserDto;
import com.creativescrapyard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // create user
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)   {
        UserDto userDto1= this.userService.createUser(userDto);
        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
    }


    // update user using  ---> @PutMapping annotation
   @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{userId}")
    public  ResponseEntity<UserDto> updateUser(@Valid @RequestBody  UserDto userDto, @PathVariable("userId") Long uId){
       UserDto updatedUser =this.userService.updateUser(userDto , uId);
       return ResponseEntity.ok(updatedUser);
    }

    // delete user
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Long userId){
        this.userService.deleteUserById(userId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully",true), HttpStatus.OK);
    }

  //get all users
    @GetMapping("/get-users")
    public  ResponseEntity<List<UserDto>>  getAllUsers(){
        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    // get single user
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto>  getSingleUser(@PathVariable("userId") Long uId){
        return ResponseEntity.ok(this.userService.getUserById(uId));
    }


}

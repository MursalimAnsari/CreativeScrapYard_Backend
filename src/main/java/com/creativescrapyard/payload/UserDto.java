package com.creativescrapyard.payload;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.implementation.bind.annotation.Empty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private Long id;

    @NotEmpty
    @Size(min = 6, message = "username must be min of 6 characters")
    private String username;

    @NotEmpty
    @Size(min = 6, max = 15 , message = "password must be in between 6 and 15 characters")
    private String password;

    @NotEmpty
    @Size(min=5, max = 12, message = "firstname should be in between 6 and 12 characters ")
    private String firstName;

    @NotEmpty
    @Size(min=5, max = 12, message = "lastname should be in between 6 and 12 characters ")
    private String lastName;

    @Email(message = "email must be valid")
    private String email;

    @NotEmpty
    @Size(min= 10, max=10 , message = "phone no must be of 10 digits ")
    private String phone;

    @NotEmpty
    @Size(min=10, max = 2000)
    private String profilePic;

}

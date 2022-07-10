package com.creativescrapyard.payload;


import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor

public class ApiResponse {

    private String message;
    private boolean success;


    public ApiResponse(String message , boolean success) {

        this.message= message;
        this.success = success;
    }
}

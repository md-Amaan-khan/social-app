package com.example.demo.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRequest {

    @NotBlank
    private String userName;
    @NotBlank
    private String password;
    @NotBlank
    private String email;
}

package com.email.notification.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestDto {

    @Size(max = 50)
    @NotBlank(message = "Name is mandatory")
    private String firstName;

    @Size(max = 50)
    @NotBlank(message = "Last Name is mandatory")
    private String lastName;

    @Email
    @NotBlank(message = "Email is mandatory")
    private String email;

    @Size(min = 6, max = 20)
    @NotBlank(message = "Password is mandatory")
    private String password;
}


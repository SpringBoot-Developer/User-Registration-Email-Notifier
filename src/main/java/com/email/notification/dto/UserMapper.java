package com.email.notification.dto;

import com.email.notification.entities.User;

public class UserMapper {

    public static User mapToUser(User user, RequestDto requestDto) {
        user.setFirstName(requestDto.getFirstName());
        user.setLastName(requestDto.getLastName());
        user.setEmail(requestDto.getEmail());
        user.setPassword(requestDto.getPassword());
        return user;
    }

}

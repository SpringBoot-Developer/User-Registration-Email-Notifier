package com.email.notification.services;

import com.email.notification.dto.RequestDto;
import com.email.notification.entities.User;

import java.util.List;

public interface UserService {

    void registerUser(RequestDto requestDto);

    List<User> getAllUsers();
}

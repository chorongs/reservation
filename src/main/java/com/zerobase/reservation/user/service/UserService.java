package com.zerobase.reservation.user.service;

import com.zerobase.reservation.user.dto.UserDto;


public interface UserService {
    String register(UserDto request);

    UserDto authenticate(UserDto request);

}

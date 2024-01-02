package com.zerobase.reservation.user.dto;

import com.zerobase.reservation.user.entity.User;
import com.zerobase.reservation.user.entity.type.Role;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto{
    private Long id;
    private String email;
    private String name;
    private String password;
    private String phone;
    private Role roles;

    public static UserDto of (User user) {

        return UserDto.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getUsername())
                .phone(user.getPhone())
                .roles(user.getRoles())
                .build();
    }

}

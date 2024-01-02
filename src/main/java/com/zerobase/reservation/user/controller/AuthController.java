package com.zerobase.reservation.user.controller;


import com.zerobase.reservation.security.TokenProvider;
import com.zerobase.reservation.user.dto.UserDto;
import com.zerobase.reservation.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final TokenProvider tokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserDto request) {

        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody UserDto request) {

        UserDto user = userService.authenticate(request);
        String token = tokenProvider
                .generateToken(user.getEmail(), user.getRoles());

        return ResponseEntity.ok(token);
    }
}

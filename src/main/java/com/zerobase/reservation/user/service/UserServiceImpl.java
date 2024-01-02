package com.zerobase.reservation.user.service;

import com.zerobase.reservation.exception.CustomException;
import com.zerobase.reservation.user.dto.UserDto;
import com.zerobase.reservation.user.entity.User;
import com.zerobase.reservation.user.entity.type.Role;
import com.zerobase.reservation.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.zerobase.reservation.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입
     * 폼을 UserDto 로 받아서 사용
     * 비밀번호 암호화
     */
    @Override
    public String register(UserDto request) {

        boolean requestUser = userRepository.existsByEmail(request.getEmail());

        if (requestUser) {
            throw new CustomException(ALREADY_REGISTERED_USER);
        }

        String encPassword =
                BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());
        User user = User.builder()
                .email(request.getEmail())
                .password(encPassword)
                .username(request.getName())
                .phone(request.getPhone())
                .roles(Role.USER)
                .build();
        userRepository.save(user);

        return "회원가입이 완료되었습니다!";
    }

    /**
     * 로그인
     */
    @Override
    public UserDto authenticate(UserDto request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        if (!this.passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(UNMATCHED_INFORMATION);
        }

        return UserDto.of(user);
    }
}

package com.zerobase.reservation.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* user */
    USER_NOT_FOUND("사용자가 없습니다.", NOT_FOUND),
    ALREADY_REGISTERED_USER("이미 존재하는 사용자입니다.", BAD_REQUEST),
    UNMATCHED_INFORMATION("아이디 또는 비밀번호가 일치하지 않습니다.", BAD_REQUEST),
    /* restaurant */
    ALREADY_EXIST_RESTAURANT("이미 존재하는 음식점입니다.", BAD_REQUEST),
    DO_NOT_EXIST_RESTAURANT("존재하지 않는 음식점입니다.", NOT_FOUND),
    EXIST_RESERVATION("예약 정보가 존재합니다.", BAD_REQUEST),
    /* reservation */
    DO_NOT_EXIST_RESERVATION("예약 정보가 존재하지 않습니다.", NOT_FOUND),
    RESERVATION_TIME_OVER("예약 시간이 지났습니다.", BAD_REQUEST),
    DO_NOT_CHECKED_VISITED("방문 확인이 되지 않았습니다.", BAD_REQUEST),
    DO_NOT_CONFIRMED("예약확정이 되지 않았습니다.", BAD_REQUEST),
    /* partner */
    USER_NOT_PARTNER("파트너 미가입 회원입니다.", BAD_REQUEST),
    /* else */
    INVALID_REQUEST("잘못된 요청입니다.", BAD_REQUEST),
    EXPIRED_TOKEN("인증 토큰이 만료되었습니다.", UNAUTHORIZED);

    private final String description;
    private final HttpStatus status;
}

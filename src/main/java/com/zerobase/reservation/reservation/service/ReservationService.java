package com.zerobase.reservation.reservation.service;

import com.zerobase.reservation.reservation.dto.ReservationDto;

import java.util.List;

public interface ReservationService {
    ReservationDto reserve(Long restaurantId, ReservationDto request);

    List<ReservationDto> getMyReservation(String email);

    ReservationDto checkIn(Long reservationId);

    ReservationDto review(String email, Long restaurantId, String reviewText);

    ReservationDto updateReservationState(Long reservationId, String state);

    boolean cancel(Long reservationId);
}

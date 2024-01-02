package com.zerobase.reservation.reservation.dto;

import com.zerobase.reservation.reservation.entity.Reservation;
import com.zerobase.reservation.reservation.entity.type.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {
    Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate date;
    @DateTimeFormat(pattern = "hh:mm")
    LocalTime time;
    Integer num;
    String userName;
    String userEmail;
    String phone;
    String specialRequest;
    Long restaurantId;
    boolean visited;
    State state;

    public static ReservationDto of(Reservation reservation) {
        return ReservationDto.builder()
                .date(reservation.getDate())
                .time(reservation.getTime())
                .num(reservation.getNum())
                .userName(reservation.getUserName())
                .userEmail(reservation.getUserEmail())
                .phone(reservation.getPhone())
                .specialRequest(reservation.getSpecialRequest())
                .restaurantId(reservation.getRestaurantId())
                .visited(reservation.isVisited())
                .state(reservation.getState())
                .build();
    }

    public static List<ReservationDto> of(List<Reservation> reservationList) {
        if (reservationList == null) {
            return Collections.emptyList();
        }

        List<ReservationDto> reservations = new ArrayList<>();
        for (Reservation r : reservationList) {
            reservations.add(ReservationDto.of(r));
        }

        return reservations;
    }
}

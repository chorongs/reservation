package com.zerobase.reservation.user.controller;

import com.zerobase.reservation.reservation.dto.ReservationDto;
import com.zerobase.reservation.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/about")
@RequiredArgsConstructor
public class UserController {

    private final ReservationService reservationService;

    @PostMapping("/my")
    public ResponseEntity<?> myReservation(Principal principal) {

        List<ReservationDto> myReservation =
                    reservationService.getMyReservation(principal.getName());

        return ResponseEntity.ok(myReservation);
    }

    @PostMapping("/my/check")
    public ResponseEntity<?> checkIn(@RequestParam Long reservationId) {

        ReservationDto myReservation = reservationService.checkIn(reservationId);

        return ResponseEntity.ok(myReservation);
    }

    @PostMapping("/my/cancel")
    public ResponseEntity<?> cancel(@RequestParam Long reservationId) {

        boolean result = reservationService.cancel(reservationId);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/my/review")
    public ResponseEntity<?> review(Principal principal,
                                    @RequestParam Long restaurantId,
                                    @RequestBody String reviewText) {

        ReservationDto myReservation =
                reservationService.review(principal.getName(), restaurantId, reviewText);

        return ResponseEntity.ok(myReservation);
    }

}

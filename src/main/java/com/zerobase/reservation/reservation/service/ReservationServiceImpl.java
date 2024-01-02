package com.zerobase.reservation.reservation.service;

import com.zerobase.reservation.exception.CustomException;
import com.zerobase.reservation.reservation.dto.ReservationDto;
import com.zerobase.reservation.reservation.entity.Reservation;
import com.zerobase.reservation.reservation.entity.type.State;
import com.zerobase.reservation.reservation.repository.ReservationRepository;
import com.zerobase.reservation.restaurant.entity.Restaurant;
import com.zerobase.reservation.restaurant.entity.Review;
import com.zerobase.reservation.restaurant.repository.RestaurantRepository;
import com.zerobase.reservation.restaurant.repository.ReviewRepository;
import com.zerobase.reservation.user.entity.User;
import com.zerobase.reservation.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.zerobase.reservation.exception.ErrorCode.*;
import static com.zerobase.reservation.reservation.entity.type.State.CONFIRM;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService{

    private final ReservationRepository reservationRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;


    // 예약하기

    @Override
    public ReservationDto reserve(Long restaurantId, ReservationDto request) {

        Reservation reservation = Reservation.builder()
                .date(request.getDate())
                .time(request.getTime())
                .num(request.getNum())
                .userName(request.getUserName())
                .userEmail(request.getUserEmail())
                .phone(request.getPhone())
                .specialRequest(request.getSpecialRequest())
                .restaurantId(restaurantId)
                .state(State.REQUEST)
                .build();
        reservationRepository.save(reservation);

        return ReservationDto.of(reservation);
    }

    /**
     * 로그인해서 마이페이지의 나의 예약 목록을 가져오는 기능
     */
    @Override
    public List<ReservationDto> getMyReservation(String email) {

        List<Reservation> reservations =
                            reservationRepository.findAllByUserEmail(email);

        return ReservationDto.of(reservations);
    }

    /**
     * ( 예약상태 : 승인 ) 이여야만 키오스크에서 예약번호로 체크인, 예약시간 10분전까지만 체크인 가능
     */
    @Override
    public ReservationDto checkIn(Long reservationId) {

        Reservation reservation = reservationRepository
                                    .findById(reservationId).orElseThrow(() ->
                                        new CustomException(DO_NOT_EXIST_RESERVATION));

        if (!reservation.getState().equals(CONFIRM)) {
            throw new CustomException(DO_NOT_CONFIRMED);
        }

        if (reservation.getTime().minusMinutes(10)
                                    .isBefore(LocalTime.now())) {
            throw new CustomException(RESERVATION_TIME_OVER);
        }

        reservation.setVisited(true);
        reservationRepository.save(reservation);

        log.info("{} checkIn", reservationId);

        return ReservationDto.of(reservation);
    }


    /**
     * 방문 후 리뷰쓰기 기능
     */
    @Override
    public ReservationDto review(String email, Long restaurantId, String reviewText) {

        Reservation reservation = reservationRepository
                                    .findByUserEmailAndRestaurantIdAndVisitedTrue(
                                        email, restaurantId).orElseThrow(() ->
                                            new CustomException(DO_NOT_EXIST_RESERVATION));

        Restaurant restaurant = restaurantRepository
                                    .findById(restaurantId).orElseThrow(() ->
                                        new CustomException(DO_NOT_EXIST_RESTAURANT));

        User user = userRepository.findByEmail(email)
                                    .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Review review = Review.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .regDt(LocalDateTime.now())
                .restaurantId(restaurantId)
                .review(reviewText)
                .build();
        reviewRepository.save(review);

        restaurant.getReviews().add(review);
        restaurantRepository.save(restaurant);

        return ReservationDto.of(reservation);
    }

    /**
     * 예약이 들어오면 MANAGER 가 ( 예약상태 : 승인/취소 ) 할 수 있는 기능,
     */
    @Override
    public ReservationDto updateReservationState(Long reservationId, String state) {

        Reservation reservation = reservationRepository
                                    .findById(reservationId).orElseThrow(() ->
                                        new CustomException(DO_NOT_EXIST_RESERVATION));

        reservation.setState(State.get(state));
        reservationRepository.save(reservation);

        log.info("reservationId : {} - changed reservationState", reservationId);

        return ReservationDto.of(reservation);
    }

    /**
     * User 가 방문하기 전 예약을 취소할 수 있는 기능
     */
    @Override
    public boolean cancel(Long reservationId) {

        Reservation reservation = reservationRepository
                                    .findById(reservationId).orElseThrow(() ->
                                        new CustomException(DO_NOT_EXIST_RESERVATION));

        if (reservation.isVisited()) {
            return false;
        }

        reservation.setState(State.CANCEL);
        reservationRepository.save(reservation);

        return true;
    }
}

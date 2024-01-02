package com.zerobase.reservation.restaurant.dto;

import com.zerobase.reservation.restaurant.entity.Restaurant;
import com.zerobase.reservation.restaurant.entity.Review;
import lombok.*;
import com.zerobase.reservation.restaurant.entity.Menu;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDto {

    private Long id;
    private String restaurantName;
    private String address;
    private String contact;
    private List<Menu> menulist;

    @DateTimeFormat(pattern = "hh:mm")
    private LocalTime open;
    @DateTimeFormat(pattern = "hh:mm")
    private LocalTime close;

    private Long userId;
    private List<ReviewDto> reviews;

    private String category;

    public static RestaurantDto of(Restaurant restaurant) {
        return RestaurantDto.builder()
                .restaurantName(restaurant.getRestaurantName())
                .address(restaurant.getAddress())
                .contact(restaurant.getContact())
                .menulist(restaurant.getMenulist())
                .open(restaurant.getOpen())
                .close(restaurant.getClose())
                .userId(restaurant.getUserId())
                .reviews(RestaurantDto.getOnlyReview(restaurant.getReviews()))
                .category(restaurant.getCategory())
                .build();
    }

    public static List<RestaurantDto> of(List<Restaurant> restaurants) {

        if (restaurants == null) {
            return Collections.emptyList();
        }

        List<RestaurantDto> restaurantDtoList = new ArrayList<>();
        for(Restaurant x : restaurants) {
            restaurantDtoList.add(RestaurantDto.of(x));
        }
        return restaurantDtoList;

    }



    public static List<ReviewDto> getOnlyReview(List<Review> reviews) {
        if (reviews == null) {
            return Collections.emptyList();
        }

        List<ReviewDto> reviewList = new ArrayList<>();
        for(Review x : reviews) {
            ReviewDto reviewDto = ReviewDto.builder()
                    .username(x.getUsername())
                    .review(x.getReview())
                    .regDt(x.getRegDt())
                    .build();
            reviewList.add(reviewDto);
        }
        return reviewList;
    }

}

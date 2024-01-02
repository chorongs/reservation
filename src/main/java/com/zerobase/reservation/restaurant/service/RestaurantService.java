package com.zerobase.reservation.restaurant.service;

import com.zerobase.reservation.restaurant.dto.RestaurantDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RestaurantService {

    RestaurantDto register(RestaurantDto restaurantDto, String userEmail);

    boolean isPartner(String email);

    Page<RestaurantDto> getRestaurantList(Pageable pageable);

    List<RestaurantDto> getListByRestaurantName(String restaurantName);

    List<RestaurantDto> getListByCategory(String category);

    RestaurantDto getRestaurantDetail(Long restaurantId);

    RestaurantDto modify(RestaurantDto restaurantDto);

    boolean delete(Long restaurantId);
}

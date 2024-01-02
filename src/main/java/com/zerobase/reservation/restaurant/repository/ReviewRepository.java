package com.zerobase.reservation.restaurant.repository;

import com.zerobase.reservation.restaurant.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    void deleteAllByRestaurantId(Long restaurantId);
}

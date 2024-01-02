package com.zerobase.reservation.restaurant.controller;

import com.zerobase.reservation.restaurant.dto.RestaurantDto;
import com.zerobase.reservation.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("/register")
    public ResponseEntity<?> register(Principal principal) {

        boolean result = restaurantService.isPartner(principal.getName());

        return ResponseEntity.ok(result);
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerSubmit(@RequestBody RestaurantDto restaurantDto,
                                                            Principal principal) {

        RestaurantDto restaurant =
                restaurantService.register(restaurantDto, principal.getName());

        return ResponseEntity.ok(restaurant);
    }

    @PostMapping("/modify")
    public ResponseEntity<?> modify(@RequestBody RestaurantDto restaurantDto) {

        RestaurantDto restaurant = restaurantService.modify(restaurantDto);

        return ResponseEntity.ok(restaurant);
    }

    @Transactional
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam Long restaurantId) {

        boolean result = restaurantService.delete(restaurantId);

        return ResponseEntity.ok(result);
    }
}

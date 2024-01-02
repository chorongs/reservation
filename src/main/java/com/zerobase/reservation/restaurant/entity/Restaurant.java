package com.zerobase.reservation.restaurant.entity;

import com.zerobase.reservation.user.entity.BaseEntity;
import lombok.*;
import org.hibernate.envers.AuditOverride;

import jakarta.persistence.*;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "restaurant")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@AuditOverride(forClass = BaseEntity.class)
public class Restaurant extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String restaurantName;
    private String address;

    private String contact;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Menu> menulist;

    private LocalTime open;
    private LocalTime close;

    private Long userId;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Review> reviews;

    private String category;

}

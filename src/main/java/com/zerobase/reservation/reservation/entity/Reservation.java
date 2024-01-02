package com.zerobase.reservation.reservation.entity;

import com.zerobase.reservation.reservation.entity.type.State;
import com.zerobase.reservation.user.entity.BaseEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.envers.AuditOverride;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reservation")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AuditOverride(forClass = BaseEntity.class)
public class Reservation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    LocalDate date;
    LocalTime time;

    @ColumnDefault("1")
    Integer num;

    String userName;
    String userEmail;
    String phone;
    String specialRequest;

    Long restaurantId;

    boolean visited = false;
    State state;

}

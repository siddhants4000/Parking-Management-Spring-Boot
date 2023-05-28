package com.example.demo.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "vehicle_number")
    private String vehicleNumber;

    @Column(name = "hours")
    private Integer hours;

    @Column(name = "rate")
    private Double rate;

    @Column(name = "created_At")
    private LocalDateTime createdAt;

}

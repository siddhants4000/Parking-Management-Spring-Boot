package com.example.demo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BookingResponse {

    private int id;

    @JsonProperty("vehicle_number")
    private String vehicleNumber;

    @JsonProperty("hours")
    private Integer hours;

    @JsonProperty("rate")
    private Double rate;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}

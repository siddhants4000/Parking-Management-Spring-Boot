package com.example.demo.response;

import com.example.demo.enums.VehicleType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RegistrationResponse {

    private int id;

    @JsonProperty("vehicle_number")
    private String vehicleNumber;

    @JsonProperty("vehicle_type")
    private VehicleType vehicleType;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("mobile_number")
    private String mobileNumber;
}

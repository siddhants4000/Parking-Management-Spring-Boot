package com.example.demo.request;

import com.example.demo.enums.VehicleType;
import lombok.*;
import org.jetbrains.annotations.NotNull;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationRequest {

    @NotNull
    private String vehicleNumber;

    @NotNull
    private VehicleType vehicleType;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String email;

    @NotNull
    private String mobileNumber;
}

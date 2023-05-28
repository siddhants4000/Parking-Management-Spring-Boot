package com.example.demo.request;

import lombok.*;
import org.checkerframework.checker.regex.qual.Regex;
import org.hibernate.validator.constraints.Length;
import org.jetbrains.annotations.NotNull;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingRequest {

    @NotNull
    @Length(min = 9, max = 10, message = "Enter valid vehicle number between 9 and 10.")
    @Regex()
    private String vehicleNumber;

    @NotNull
    @Valid
    @Length(min = 1, max = 24, message = "Enter valid number of hours between 1 and 24.")
    private Integer hours;
}

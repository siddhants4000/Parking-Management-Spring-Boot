package com.example.demo.controller;

import com.example.demo.model.WrapperResponse;
import com.example.demo.request.BookingRequest;
import com.example.demo.response.BookingResponse;
import com.example.demo.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("booking/")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @PostMapping("user/add")
    public WrapperResponse<BookingResponse> addBooking(@RequestBody BookingRequest bookingRequest) {
        return bookingService.addBooking(bookingRequest);
    }

    @PostMapping("admin/delete")
    public WrapperResponse<BookingResponse> deleteBooking(@RequestParam String vehicleNumber) {
        return bookingService.deleteBooking(vehicleNumber);
    }

    @GetMapping("user/parking-space")
    public String getParkingSpace() {
        return bookingService.getParkingSpace();
    }
}

package com.example.demo.service;

import com.example.demo.entity.Booking;
import com.example.demo.entity.Registration;
import com.example.demo.enums.StatusCode;
import com.example.demo.enums.VehicleType;
import com.example.demo.model.Status;
import com.example.demo.model.WrapperResponse;
import com.example.demo.repo.BookingRepository;
import com.example.demo.repo.RegistrationRepository;
import com.example.demo.request.BookingRequest;
import com.example.demo.response.BookingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    RegistrationRepository registrationRepository;

    @Autowired
    EmailSenderService emailSenderService;

    @Value("${sendMailTo}")
    private String emailTo;

    @Value("${parking.capacity}")
    Long parkingCapacity;

    public WrapperResponse<BookingResponse> addBooking(BookingRequest bookingRequest) {
        try {
            if(Objects.isNull(registrationRepository.findByVehicleNumber(bookingRequest.getVehicleNumber()))) {
                Status status= Status.builder()
                        .message("Vehicle is not registered.")
                        .code(StatusCode.SUCCESS.getCode())
                        .success(Boolean.TRUE)
                        .build();

                return WrapperResponse.<BookingResponse>builder()
                        .status(status)
                        .build();
            } else if (parkingCapacity<=0) {
                Status status= Status.builder()
                        .message("Parking is full. Please try again later.")
                        .code(StatusCode.SUCCESS.getCode())
                        .success(Boolean.TRUE)
                        .build();

                return WrapperResponse.<BookingResponse>builder()
                        .status(status)
                        .build();
            } else if (Objects.nonNull(bookingRepository.findByVehicleNumber(bookingRequest.getVehicleNumber()))) {
                Status status= Status.builder()
                        .message("Parking is already booked for this Vehicle.")
                        .code(StatusCode.SUCCESS.getCode())
                        .success(Boolean.TRUE)
                        .build();

                return WrapperResponse.<BookingResponse>builder()
                        .status(status)
                        .build();
            } else {
                Registration registration= registrationRepository.findByVehicleNumber(bookingRequest.getVehicleNumber());

                Booking newBooking= Booking.builder()
                        .vehicleNumber(bookingRequest.getVehicleNumber())
                        .hours(bookingRequest.getHours())
                        .rate(calculateRate(bookingRequest.getHours(), registration.getVehicleType()))
                        .createdAt(LocalDateTime.now())
                        .build();

                Status status= Status.builder()
                        .message("Parking Booked Successfully.")
                        .code(StatusCode.SUCCESS.getCode())
                        .success(Boolean.TRUE)
                        .build();

                parkingCapacity=parkingCapacity-1;

                bookingRepository.save(newBooking);

                String subject= "Invoice for Parking Booking.";

                String body= "Parking Details are- \n"+ bookingRepository.findById(newBooking.getId()) +"\nPlease pay the amount";

//                emailSenderService.sendEmail(registration.getEmail(), subject, body);

                emailSenderService.sendEmail(emailTo, subject, body);

                return WrapperResponse.<BookingResponse>builder()
                        .data(BookingResponse.builder()
                                .id(newBooking.getId())
                                .vehicleNumber(newBooking.getVehicleNumber())
                                .hours(newBooking.getHours())
                                .rate(newBooking.getRate())
                                .createdAt(newBooking.getCreatedAt())
                                .build())
                        .status(status)
                        .build();
            }
        } catch (Exception e) {
            Status status= Status.builder()
                    .message("Something Went Wrong.")
                    .code(StatusCode.BAD_REQUEST.getCode())
                    .success(Boolean.TRUE)
                    .build();

            return WrapperResponse.<BookingResponse>builder()
                    .status(status)
                    .build();
        }
    }

    public WrapperResponse<BookingResponse> deleteBooking(String vehicleNumber) {
        try {
            if(Objects.isNull(bookingRepository.findByVehicleNumber(vehicleNumber))) {
                Status status= Status.builder()
                        .message("No Booking for this Vehicle found.")
                        .code(StatusCode.SUCCESS.getCode())
                        .success(Boolean.TRUE)
                        .build();

                return WrapperResponse.<BookingResponse>builder()
                        .status(status)
                        .build();
            } else {
                Booking booking= bookingRepository.findByVehicleNumber(vehicleNumber);

                Status status= Status.builder()
                        .code(StatusCode.SUCCESS.getCode())
                        .message("Booking has been deleted successfully.")
                        .success(Boolean.TRUE)
                        .build();

                String subject= "Parking Booking Deleted Successfully.";

                String body= "Parking Details are- \n"+ bookingRepository.findById(booking.getId()) +"\nPlease pay the amount.";

//                emailSenderService.sendEmail(registrationRepository.findByVehicleNumber(vehicleNumber).getEmail(), subject, body);

                emailSenderService.sendEmail(emailTo, subject, body);

                parkingCapacity=parkingCapacity+1;

                bookingRepository.deleteById(booking.getId());

                return WrapperResponse.<BookingResponse>builder()
                        .data(BookingResponse.builder()
                                .id(booking.getId())
                                .vehicleNumber(booking.getVehicleNumber())
                                .hours(booking.getHours())
                                .rate(booking.getRate())
                                .createdAt(booking.getCreatedAt())
                                .build())
                        .status(status)
                        .build();
            }
        } catch (Exception e) {
            Status status= Status.builder()
                    .message("Something Went Wrong.")
                    .code(StatusCode.BAD_REQUEST.getCode())
                    .success(Boolean.TRUE)
                    .build();

            return WrapperResponse.<BookingResponse>builder()
                    .status(status)
                    .build();
        }
    }

    private double calculateRate(int hours, VehicleType vehicleType) {
        double base;
        if(VehicleType.CAR.equals(vehicleType)){
            base=50;
        } else {
            base=30;
        }
        double variable= (hours-1)*10;
        double total=base+variable;
        return total;
    }

    public String getParkingSpace() {
        return "Total parking space left- " + parkingCapacity;
    }
}

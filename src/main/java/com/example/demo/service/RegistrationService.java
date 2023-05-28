package com.example.demo.service;

import com.example.demo.entity.Registration;
import com.example.demo.enums.StatusCode;
import com.example.demo.model.Status;
import com.example.demo.model.WrapperResponse;
import com.example.demo.repo.RegistrationRepository;
import com.example.demo.request.RegistrationRequest;
import com.example.demo.response.RegistrationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class RegistrationService {

    @Autowired
    RegistrationRepository registrationRepository;

    @Autowired
    EmailSenderService emailSenderService;

    @Value("${sendMailTo}")
    private String emailTo;

    public WrapperResponse<RegistrationResponse> addRegistration(RegistrationRequest registrationRequest) {
        try{
            if(Objects.isNull(registrationRepository.findByVehicleNumber(registrationRequest.getVehicleNumber())) && Objects.isNull(registrationRepository.findByEmail(registrationRequest.getEmail())) && Objects.isNull(registrationRepository.findByMobileNumber(registrationRequest.getMobileNumber()))){
                Registration newRegistration= Registration.builder()
                        .vehicleNumber(registrationRequest.getVehicleNumber())
                        .vehicleType(registrationRequest.getVehicleType())
                        .firstName(registrationRequest.getFirstName())
                        .lastName(registrationRequest.getLastName())
                        .email(registrationRequest.getEmail())
                        .mobileNumber(registrationRequest.getMobileNumber())
                        .build();

                registrationRepository.save(newRegistration);

                Status status= Status.builder()
                        .code(StatusCode.SUCCESS.getCode())
                        .message("User registered successfully.")
                        .success(Boolean.TRUE)
                        .build();

                String subject= "Registration Successful.";

                String body= "Registration Details are- \n "+ registrationRepository.findById(newRegistration.getId());

//                emailSenderService.sendEmail(newRegistration.getEmail(), subject, body);

                emailSenderService.sendEmail(emailTo, subject, body);

                return WrapperResponse.<RegistrationResponse>builder()
                        .data(RegistrationResponse.builder()
                                .id(newRegistration.getId())
                                .vehicleNumber(newRegistration.getVehicleNumber())
                                .vehicleType(newRegistration.getVehicleType())
                                .firstName(newRegistration.getFirstName())
                                .lastName(newRegistration.getLastName())
                                .email(newRegistration.getEmail())
                                .mobileNumber(newRegistration.getMobileNumber())
                                .build())
                        .status(status)
                        .build();
            } else if (Objects.nonNull(registrationRepository.findByVehicleNumber(registrationRequest.getVehicleNumber())) && Objects.isNull(registrationRepository.findByEmail(registrationRequest.getEmail())) && Objects.isNull(registrationRepository.findByMobileNumber(registrationRequest.getMobileNumber()))){
                Status status= Status.builder()
                        .code(StatusCode.BAD_REQUEST.getCode())
                        .message("User with this vehicle number is already registered.")
                        .success(Boolean.TRUE)
                        .build();

                return WrapperResponse.<RegistrationResponse>builder()
                        .status(status)
                        .build();
            } else if (Objects.nonNull(registrationRepository.findByEmail(registrationRequest.getEmail())) && Objects.isNull(registrationRepository.findByVehicleNumber(registrationRequest.getVehicleNumber())) && Objects.isNull(registrationRepository.findByMobileNumber(registrationRequest.getMobileNumber()))){
                Status status= Status.builder()
                        .code(StatusCode.BAD_REQUEST.getCode())
                        .message("User with this email is already registered.")
                        .success(Boolean.TRUE)
                        .build();

                return WrapperResponse.<RegistrationResponse>builder()
                        .status(status)
                        .build();
            } else if (Objects.nonNull(registrationRepository.findByMobileNumber(registrationRequest.getMobileNumber())) && Objects.isNull(registrationRepository.findByEmail(registrationRequest.getEmail())) && Objects.isNull(registrationRepository.findByVehicleNumber(registrationRequest.getVehicleNumber()))){
                Status status= Status.builder()
                        .code(StatusCode.BAD_REQUEST.getCode())
                        .message("User with this mobile number is already registered.")
                        .success(Boolean.TRUE)
                        .build();

                return WrapperResponse.<RegistrationResponse>builder()
                        .status(status)
                        .build();
            } else {
                Status status= Status.builder()
                        .code(StatusCode.BAD_REQUEST.getCode())
                        .message("User is already registered.")
                        .success(Boolean.TRUE)
                        .build();

                return WrapperResponse.<RegistrationResponse>builder()
                        .status(status)
                        .build();
            }
        } catch (Exception e){
            Status status= Status.builder()
                    .code(StatusCode.BAD_REQUEST.getCode())
                    .message("Something Went Wrong.")
                    .success(Boolean.TRUE)
                    .build();

            return WrapperResponse.<RegistrationResponse>builder()
                    .status(status)
                    .build();
        }
    }

    public WrapperResponse<RegistrationResponse> deleteRegistration(String vehicleNumber) {
        try {
            if (Objects.isNull(registrationRepository.findByVehicleNumber(vehicleNumber))) {
                Status status = Status.builder()
                        .code(StatusCode.BAD_REQUEST.getCode())
                        .message("Vehicle is not registered.")
                        .success(Boolean.TRUE)
                        .build();

                return WrapperResponse.<RegistrationResponse>builder()
                        .status(status)
                        .build();
            } else {
                Registration registration= registrationRepository.findByVehicleNumber(vehicleNumber);

                Status status= Status.builder()
                        .code(StatusCode.SUCCESS.getCode())
                        .message("Registration has been deleted successfully.")
                        .success(Boolean.TRUE)
                        .build();

                String subject= "Registration Deleted Successful.";

                String body= "Registration Details are- \n "+ registrationRepository.findById(registration.getId());

//                emailSenderService.sendEmail(registration.getEmail(), subject, body);

                emailSenderService.sendEmail(emailTo, subject, body);

                registrationRepository.deleteById(registration.getId());

                return WrapperResponse.<RegistrationResponse>builder()
                        .data(RegistrationResponse.builder()
                                .id(registration.getId())
                                .vehicleNumber(registration.getVehicleNumber())
                                .vehicleType(registration.getVehicleType())
                                .firstName(registration.getFirstName())
                                .lastName(registration.getLastName())
                                .email(registration.getEmail())
                                .mobileNumber(registration.getMobileNumber())
                                .build())
                        .status(status)
                        .build();
            }
        } catch (Exception e) {
            Status status = Status.builder()
                    .code(StatusCode.BAD_REQUEST.getCode())
                    .message("Something Went Wrong.")
                    .success(Boolean.TRUE)
                    .build();

            return WrapperResponse.<RegistrationResponse>builder()
                    .status(status)
                    .build();
        }
    }

}

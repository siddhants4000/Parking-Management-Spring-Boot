package com.example.demo.controller;

import com.example.demo.model.WrapperResponse;
import com.example.demo.request.RegistrationRequest;
import com.example.demo.response.RegistrationResponse;
import com.example.demo.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("registration/")
public class RegistrationController {

    @Autowired
    RegistrationService registrationService;

    @PostMapping("user/add")
    public WrapperResponse<RegistrationResponse> addRegistration(@RequestBody RegistrationRequest registrationRequest) {
        return registrationService.addRegistration(registrationRequest);
    }

    @PostMapping("admin/delete")
    public WrapperResponse<RegistrationResponse> deleteRegistration(@RequestParam String vehicleNumber) {
        return registrationService.deleteRegistration(vehicleNumber);
    }
}

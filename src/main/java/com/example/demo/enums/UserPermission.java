package com.example.demo.enums;

public enum UserPermission {
    REGISTRATION_READ("registration:read"),
    REGISTRATION_WRITE("registration:write"),
    BOOKING_READ("booking:read"),
    BOOKING_WRITE("booking:write");

    private final String permission;

    UserPermission(String permission){
        this.permission=permission;
    }

    public String getPermission() {
        return permission;
    }
}

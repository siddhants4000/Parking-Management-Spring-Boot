package com.example.demo.enums;

import com.google.common.collect.Sets;

import java.util.Set;

import static com.example.demo.enums.UserPermission.*;

public enum UserRole {
    USER(Sets.newHashSet(REGISTRATION_WRITE, BOOKING_READ, BOOKING_WRITE)),
    ADMIN(Sets.newHashSet(REGISTRATION_READ, REGISTRATION_WRITE, BOOKING_READ, BOOKING_WRITE));

    private final Set<UserPermission> permissions;

    UserRole(Set<UserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<UserPermission> getPermissions() {
        return permissions;
    }
}

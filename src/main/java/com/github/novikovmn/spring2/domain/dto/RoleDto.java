package com.github.novikovmn.spring2.domain.dto;

import com.github.novikovmn.spring2.domain.Role;

public enum RoleDto {
    CUSTOMER,
    MANAGER,
    ADMIN;

    public static RoleDto fromRole(String role) {
        return valueOf(role);
    }
}

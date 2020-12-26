package com.github.novikovmn.spring2.domain.dto;

public enum RoleDto {
    CUSTOMER,
    MANAGER,
    ADMIN;

    public String getRole() {
        return "ROLE_" + this.name();
    }
    public static RoleDto fromRole(String role) {
        return valueOf(role.split("_")[1]);
    }
}

package com.github.novikovmn.spring2.domain.dto;

import com.github.novikovmn.spring2.exception.UserTypeNotFoundException;

public enum RoleDto {
    CUSTOMER,
    MANAGER,
    ADMIN;

    public String getRole() {
        return "ROLE_" + this.name();
    }
}

package com.github.novikovmn.spring2.service;

import com.github.novikovmn.spring2.domain.Role;
import com.github.novikovmn.spring2.exception.RoleNotFoundException;
import com.github.novikovmn.spring2.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getByName(String name) {
        Optional<Role> roleOptional = roleRepository.findByName(name);
        if (roleOptional.isPresent()) {
            return roleOptional.get();
        } else {
            throw new RoleNotFoundException(String.format("Роль с именем %s не найдена.", name));
        }
    }
}

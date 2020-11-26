package com.github.novikovmn.spring2.repository;

import com.github.novikovmn.spring2.domain.Role;
import com.github.novikovmn.spring2.domain.User;
import com.github.novikovmn.spring2.domain.dto.UserType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findAll();

    List<User> findByRoles(Role role);
}

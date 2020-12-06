package com.github.novikovmn.spring2.repository;

import com.github.novikovmn.spring2.domain.Role;
import com.github.novikovmn.spring2.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findAll();
    List<User> findAllByRoles(Role role);
    Optional<User> findByPhone(String phone);
}

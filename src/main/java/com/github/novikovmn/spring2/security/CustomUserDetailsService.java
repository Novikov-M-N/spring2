package com.github.novikovmn.spring2.security;

import com.github.novikovmn.spring2.domain.User;
import com.github.novikovmn.spring2.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@AllArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByPhone(s);
        if (user.isPresent()) {
            return new CustomPrincipal(user.get());
        }
        throw new UsernameNotFoundException(String.format("Пользователь с телефоном %s не найден", s));
    }
}

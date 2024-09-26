package com.example.jewelryspringapplication.Services.AuthenticationService;

import com.example.jewelryspringapplication.Exceptions.AccountVerificationException;
import com.example.jewelryspringapplication.Models.Users.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;

    @Autowired
    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> user = userService.findUserByEmail(email);

        assert user.isPresent();

        if (user.get().getRegistrationCode() != null) { throw new AccountVerificationException("Account is not verified"); }

        return map(user.get());
    }

    private UserDetails map(User user) {

        return new org.springframework.security.core.userdetails.User
        (user.getEmail(),(user.getPassword()), Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole())));
    }
}

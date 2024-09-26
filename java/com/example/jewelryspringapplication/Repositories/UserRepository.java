package com.example.jewelryspringapplication.Repositories;

import com.example.jewelryspringapplication.Models.Users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    User findByRegistrationCode(String registrationCode);
    List<User> findAllByIsSubscribedTrue();

}

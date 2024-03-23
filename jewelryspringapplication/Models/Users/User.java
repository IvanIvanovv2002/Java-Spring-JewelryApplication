package com.example.jewelryspringapplication.Models.Users;

import com.example.jewelryspringapplication.Models.Jewelries.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String registrationCode;

    @Column(nullable = false)
    private Boolean isSubscribed;

    @Column(nullable = false)
    private String role;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<BaseEntity> cartItems;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<BaseEntity> wishlist;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.registrationCode = UUID.randomUUID().toString();
        this.role = "USER";
        this.isSubscribed = false;
        this.cartItems = new ArrayList<>();
        this.wishlist = new ArrayList<>();
    }

    public User() { };

}

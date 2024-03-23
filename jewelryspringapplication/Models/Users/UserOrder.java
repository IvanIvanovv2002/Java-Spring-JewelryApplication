package com.example.jewelryspringapplication.Models.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name = "orders")
@Getter
@Setter
public class UserOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String delivery;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false,columnDefinition = "TEXT")
    private String message;

    @Column(nullable = false)
    private Double totalPrice;

    @Column(nullable = false)
    private String jsonProducts;

    public UserOrder(String firstName, String lastName, String city, String delivery, String address, String email, String phoneNumber, String message, Double totalPrice, String jsonProducts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.delivery = delivery;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.message = message;
        this.totalPrice = totalPrice;
        this.jsonProducts = jsonProducts;
    }


    public UserOrder() { }

}

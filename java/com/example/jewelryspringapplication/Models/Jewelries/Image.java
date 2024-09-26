package com.example.jewelryspringapplication.Models.Jewelries;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "images")
@Getter
@Setter
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "path")
    private String path;

    public Image(String path) {
        this.path = path;
    }

    @ManyToOne
    private BaseEntity jewelry;

    public Image() { }
}
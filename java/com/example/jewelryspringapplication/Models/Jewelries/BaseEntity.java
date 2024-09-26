package com.example.jewelryspringapplication.Models.Jewelries;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Table(name = "jewelry")
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="discriminator", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private Double price;

    @Column(insertable=false, updatable=false)
    private String discriminator;

    @Column(nullable = false,name = "gemstone_type")
    private String gemstoneType;

    @Column(nullable = false,name = "key_words")
    private String keyWords;

    @Column(nullable = false)
    private Boolean notInStock;

    @Column(nullable = false)
    private String description;

    @Column
    private String video;

    @Column(name = "main_image",nullable = false)
    private String mainPicturePath;


    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "jewelry_id")
    private List<Image> imagesPath;

    @Column
    private String origin;

    public BaseEntity(String name, Double price, String gemstone,String keyWords, Boolean notInStock, String description, String mainPicturePath, List<Image> imagesPath,String origin) {
        this.name = name;
        this.price = price;
        this.gemstoneType = gemstone;
        this.keyWords = keyWords;
        this.notInStock = notInStock;
        this.description = description;
        this.mainPicturePath = mainPicturePath;
        this.imagesPath = imagesPath;
        this.origin = origin;
        this.video = null;
    }

    public BaseEntity() { }
}

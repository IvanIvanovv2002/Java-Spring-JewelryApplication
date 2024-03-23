package com.example.jewelryspringapplication.Models.Jewelries;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@DiscriminatorValue("Ring")
@Entity
@Getter
@Setter
public class Ring extends BaseEntity {

    public Ring(String name, Double price, String gemstone,String keyWords, Boolean notInStock, String description, String mainPicturePath, List<Image> imagesPath,String origin) {
        super(name, price, gemstone,keyWords, notInStock, description, mainPicturePath, imagesPath,origin);
    }


    public Ring() { }
}

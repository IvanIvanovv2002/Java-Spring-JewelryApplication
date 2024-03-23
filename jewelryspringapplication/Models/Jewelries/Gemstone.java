package com.example.jewelryspringapplication.Models.Jewelries;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@DiscriminatorValue("Gemstone")
@Getter
@Setter
public class Gemstone extends BaseEntity {

    public Gemstone(String name, Double price, String gemstone,String keyWords, Boolean notInStock, String description, String mainPicturePath, List<Image> imagesPath, String origin) {
        super(name, price, gemstone,keyWords, notInStock, description, mainPicturePath, imagesPath,origin);
    }

    public Gemstone() { }
}

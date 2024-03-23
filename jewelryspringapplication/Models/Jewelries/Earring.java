package com.example.jewelryspringapplication.Models.Jewelries;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@DiscriminatorValue("Earring")
@Entity
@Getter
@Setter
public class Earring extends BaseEntity {

    public Earring(String name, Double price, String gemstone,String keyWords, Boolean notInStock, String description, String mainPicturePath, List<Image> imagesPath,String origin) {
        super(name, price, gemstone,keyWords, notInStock, description, mainPicturePath, imagesPath,origin);
    }

    public Earring() { }
}

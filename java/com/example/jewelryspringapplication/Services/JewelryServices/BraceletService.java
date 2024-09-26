package com.example.jewelryspringapplication.Services.JewelryServices;

import com.example.jewelryspringapplication.Models.Jewelries.BaseEntity;
import com.example.jewelryspringapplication.Util.RandomizeCollections;
import com.example.jewelryspringapplication.Models.Jewelries.Bracelet;
import com.example.jewelryspringapplication.Repositories.BraceletRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class BraceletService implements RandomizeCollections {

    private final BraceletRepository braceletRepository;

    public BraceletService(BraceletRepository braceletRepository) {
        this.braceletRepository = braceletRepository;
    }

    @Override
    public List<BaseEntity> randomizeItems(int itemsExtracted) {
        List<Bracelet> items = this.braceletRepository.findAll();

        Collections.shuffle(items);

        return new ArrayList<>(items.subList(0, Math.min(items.size(), itemsExtracted)));
    }

}

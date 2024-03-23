package com.example.jewelryspringapplication.Services.JewelryServices;

import com.example.jewelryspringapplication.Models.Jewelries.BaseEntity;
import com.example.jewelryspringapplication.Models.Jewelries.Gemstone;
import com.example.jewelryspringapplication.Repositories.GemstoneRepository;
import com.example.jewelryspringapplication.Util.RandomizeCollections;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class GemstoneService implements RandomizeCollections {

    private final GemstoneRepository gemstoneRepository;

    public GemstoneService(GemstoneRepository gemstoneRepository) {
        this.gemstoneRepository = gemstoneRepository;
    }

    @Override
    public List<BaseEntity> randomizeItems(int itemsExtracted) {
        List<Gemstone> items = this.gemstoneRepository.findAll();

        Collections.shuffle(items);

        return new ArrayList<>(items.subList(0, Math.min(items.size(), itemsExtracted)));
    }
}

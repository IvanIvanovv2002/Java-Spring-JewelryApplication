package com.example.jewelryspringapplication.Services.JewelryServices;

import com.example.jewelryspringapplication.Models.Jewelries.BaseEntity;
import com.example.jewelryspringapplication.Util.RandomizeCollections;
import com.example.jewelryspringapplication.Models.Jewelries.Ring;
import com.example.jewelryspringapplication.Repositories.RingRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class RingService implements RandomizeCollections {
    private final RingRepository ringRepository;

    public RingService(RingRepository ringRepository) {
        this.ringRepository = ringRepository;
    }

    @Override
    public List<BaseEntity> randomizeItems(int itemsExtracted) {
        List<Ring> items = this.ringRepository.findAll();

        Collections.shuffle(items);

        return new ArrayList<>(items.subList(0, Math.min(items.size(), itemsExtracted)));
    }
}

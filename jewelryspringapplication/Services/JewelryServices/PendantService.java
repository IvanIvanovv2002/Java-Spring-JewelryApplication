package com.example.jewelryspringapplication.Services.JewelryServices;

import com.example.jewelryspringapplication.Models.Jewelries.BaseEntity;
import com.example.jewelryspringapplication.Util.RandomizeCollections;
import com.example.jewelryspringapplication.Models.Jewelries.Pendant;
import com.example.jewelryspringapplication.Repositories.PendantRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PendantService implements RandomizeCollections {

    private final PendantRepository pendantRepository;

    public PendantService(PendantRepository pendantRepository) {
        this.pendantRepository = pendantRepository;
    }


    @Override
    public List<BaseEntity> randomizeItems(int itemsExtracted) {
        List<Pendant> items = this.pendantRepository.findAll();

        Collections.shuffle(items);

        return new ArrayList<>(items.subList(0, Math.min(items.size(), itemsExtracted)));
    }
}

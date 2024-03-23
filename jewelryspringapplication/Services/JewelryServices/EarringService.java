package com.example.jewelryspringapplication.Services.JewelryServices;

import com.example.jewelryspringapplication.Models.Jewelries.BaseEntity;
import com.example.jewelryspringapplication.Util.RandomizeCollections;
import com.example.jewelryspringapplication.Models.Jewelries.Earring;
import com.example.jewelryspringapplication.Repositories.EarringRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class EarringService implements RandomizeCollections {

    private final EarringRepository earringRepository;

    @Autowired
    public EarringService(EarringRepository earringRepository) {
        this.earringRepository = earringRepository;
    }

    @Override
    public List<BaseEntity> randomizeItems(int itemsExtracted) {
        List<Earring> items = this.earringRepository.findAll();

        Collections.shuffle(items);

        return new ArrayList<>(items.subList(0, Math.min(items.size(), itemsExtracted)));
    }

}

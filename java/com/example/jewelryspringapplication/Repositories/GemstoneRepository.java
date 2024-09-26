package com.example.jewelryspringapplication.Repositories;

import com.example.jewelryspringapplication.Models.Jewelries.Gemstone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GemstoneRepository  extends JpaRepository<Gemstone,Long> {
}

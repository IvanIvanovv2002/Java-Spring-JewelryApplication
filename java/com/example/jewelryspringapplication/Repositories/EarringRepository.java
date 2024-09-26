package com.example.jewelryspringapplication.Repositories;

import com.example.jewelryspringapplication.Models.Jewelries.Earring;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EarringRepository extends JpaRepository<Earring,Long> {

}

package com.example.jewelryspringapplication.Repositories;

import com.example.jewelryspringapplication.Models.Jewelries.Bracelet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BraceletRepository extends JpaRepository<Bracelet,Long> {

}

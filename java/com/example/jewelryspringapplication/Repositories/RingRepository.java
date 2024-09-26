package com.example.jewelryspringapplication.Repositories;

import com.example.jewelryspringapplication.Models.Jewelries.Ring;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RingRepository extends JpaRepository<Ring,Long> { }

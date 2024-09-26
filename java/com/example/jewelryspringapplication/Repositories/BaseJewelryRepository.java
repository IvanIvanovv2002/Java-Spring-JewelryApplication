package com.example.jewelryspringapplication.Repositories;

import com.example.jewelryspringapplication.Models.Jewelries.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BaseJewelryRepository extends JpaRepository<BaseEntity,Long> {

    List<BaseEntity> findAllByDiscriminator(String discriminator);
    Optional<BaseEntity> findByDiscriminatorAndId(String discriminator, Long id);
    Page<BaseEntity> findAllByOrderByPriceAsc(Pageable pageable);
    Page<BaseEntity> findAllByOrderByPriceDesc(Pageable pageable);
    Page<BaseEntity> findAllByOrderByNameAsc(Pageable pageable);
    Page<BaseEntity> findAllByOrderByNameDesc(Pageable pageable);
    Page<BaseEntity> findAllByDiscriminatorOrderByPriceAsc(String discriminator, Pageable pageable);
    Page<BaseEntity> findAllByDiscriminatorOrderByPriceDesc(String discriminator, Pageable pageable);
    Page<BaseEntity> findAllByDiscriminator(String discriminator, Pageable pageable);
    Page<BaseEntity> findAllByDiscriminatorOrderByNameAsc(String discriminator, Pageable pageable);
    Page<BaseEntity> findAllByDiscriminatorOrderByNameDesc(String discriminator, Pageable pageable);
    Page<BaseEntity>findAllByDiscriminatorNotAndKeyWordsContainingIgnoreCase(String discriminator, String keyWords, Pageable pageable);
    Page<BaseEntity> findAllByKeyWordsContainingIgnoreCase(String keyWords, Pageable pageable);

}

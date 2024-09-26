package com.example.jewelryspringapplication.Services.JewelryServices;

import com.example.jewelryspringapplication.Models.Jewelries.BaseEntity;
import com.example.jewelryspringapplication.Repositories.BaseJewelryRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.jewelryspringapplication.Constants.BasicConstants.*;

@Service
public class BaseJewelryService {

    private final BaseJewelryRepository baseJewelryRepository;

    public BaseJewelryService(BaseJewelryRepository baseJewelryRepository) {
        this.baseJewelryRepository = baseJewelryRepository;
    }

   public BaseEntity findByIdAndDiscriminator(Long id, String discriminator) {
        return baseJewelryRepository.findByDiscriminatorAndId(discriminator, id).orElseThrow(() -> new RuntimeException("error with finding the jewelry"));
   }
   public List<BaseEntity> findAllByDiscriminator(String discriminator) {
        return baseJewelryRepository.findAllByDiscriminator(discriminator);
   }

   public BaseEntity findById(Long id) {
        return this.baseJewelryRepository.findById(id).orElseThrow();
   }

   public void removeItem(BaseEntity entity) { this.baseJewelryRepository.delete(entity); }

    public List<BaseEntity> findCommonJewelries(String discriminator, BaseEntity jewelry) {
        List<BaseEntity> jewelries = this.findAllByDiscriminator(discriminator);

        jewelries.remove(jewelry);

        Collections.shuffle(jewelries);

        return new ArrayList<>(jewelries.subList(0, Math.min(jewelries.size(), 3)));
    }

    public List<BaseEntity> randomizeItems(int itemsExtracted, List<BaseEntity> jewelries) {

        Collections.shuffle(jewelries);

        return new ArrayList<>(jewelries.subList(0, Math.min(jewelries.size(), itemsExtracted)));
    }

    public Page<BaseEntity> findItems(int page, String sortType, String jewelryType, String keyWords) {
        if ("Jewelry".equals(jewelryType)) {
            return findAllByJewelriesNotAndKeyWordsContainingIgnoreCase(keyWords, page);
        } else if (sortType != null && jewelryType == null) {
            return findAllBySortType(sortType, page);
        } else if (sortType != null) {
            return findAllByJewelryTypeWithSorting(sortType, jewelryType, page);
        } else if (keyWords != null) {
            return findAllByKeyWordsContainingIgnoreCase(keyWords, page);
        } else if (jewelryType != null) {
            return findAllByJewelryTypeWithSorting(null, jewelryType, page);
        } else {
            return findAll(page);
        }
    }

    public void changeProductName(String newName, BaseEntity product) {
        product.setName(newName);
        this.save(product);
    }

    public void changeProductDescription(String newDescription, BaseEntity product) {
        product.setDescription(newDescription);
        this.save(product);
    }

    public void changeProductPrice(Double newPrice, BaseEntity product) {
        product.setPrice(newPrice);
        this.save(product);
    }

    public void changeProductStock(String stock, BaseEntity product) {
        switch (stock) {
            case NOT_IN_STOCK ->  { product.setNotInStock(true); product.setPrice(null); }
            case IN_STOCK -> product.setNotInStock(false);
        }
        this.save(product);
    }

    /**
   -------------------------------------------------------------------------------------------------------------------------------------------------------
                                             Private methods for searching and sorting products
    */

    private void save(BaseEntity baseEntity) {
        this.baseJewelryRepository.save(baseEntity);
    }

   private Page<BaseEntity> findAllByJewelriesNotAndKeyWordsContainingIgnoreCase(String keyWord,int page) {
       return this.baseJewelryRepository.findAllByDiscriminatorNotAndKeyWordsContainingIgnoreCase("Gemstone",keyWord,pageFound(page));
   }
   private Page<BaseEntity> findAllByKeyWordsContainingIgnoreCase(String keyWord,int page) {
       return this.baseJewelryRepository.findAllByKeyWordsContainingIgnoreCase(keyWord,pageFound(page));
   }

   private Page<BaseEntity> findAllBySortType(String sortType, int page) {
       switch (sortType) {
           case PRICE_ASCENDING -> { return findAllSortedByPriceAsc(page); }
           case PRICE_DESCENDING -> { return findAllSortedByPriceDesc(page); }
           case ALPHABETICAL_ASCENDING -> { return findAllSortedByAlphabeticalAsc(page); }
           case ALPHABETICAL_DESCENDING -> { return findAllSortedByAlphabeticalDesc(page); }
           default -> { return findAll(page); }
       }
   }
    private Page<BaseEntity> findAllByJewelryTypeWithSorting(String sortType, String jewelryType, int page) {
        switch (sortType) {
            case PRICE_ASCENDING -> { return findAllByDiscriminatorOrderByPriceAsc(jewelryType,page); }
            case PRICE_DESCENDING -> {return findAllByDiscriminatorOrderByPriceDesc(jewelryType,page); }
            case ALPHABETICAL_ASCENDING -> { return findAllByDiscriminatorOrderByNameAsc(jewelryType,page); }
            case ALPHABETICAL_DESCENDING -> { return findAllByDiscriminatorOrderByNameDesc(jewelryType,page); }
            case null, default -> { return findAllByDiscriminator(jewelryType,page); }
        }
    }

    private Page<BaseEntity> findAll(int page) {
        return baseJewelryRepository.findAll(pageFound(page));
    }

    private Page<BaseEntity> findAllByDiscriminator(String discriminator, int page) {
        return baseJewelryRepository.findAllByDiscriminator(discriminator,pageFound(page));
    }

    private Page<BaseEntity> findAllSortedByPriceAsc(int page) {
        return baseJewelryRepository.findAllByOrderByPriceAsc(pageFound(page));
    }

    private Page<BaseEntity> findAllSortedByPriceDesc(int page) {
        return baseJewelryRepository.findAllByOrderByPriceDesc(pageFound(page));
    }

    private Page<BaseEntity> findAllSortedByAlphabeticalDesc(int page) {
        return baseJewelryRepository.findAllByOrderByNameDesc(pageFound(page));
    }

    private Page<BaseEntity> findAllSortedByAlphabeticalAsc(int page) {
        return baseJewelryRepository.findAllByOrderByNameAsc(pageFound(page));
    }

    private Page<BaseEntity> findAllByDiscriminatorOrderByPriceAsc(String discriminator, int page) {
        return baseJewelryRepository.findAllByDiscriminatorOrderByPriceAsc(discriminator,pageFound(page));
    }

    private Page<BaseEntity> findAllByDiscriminatorOrderByPriceDesc(String discriminator, int page) {
        return baseJewelryRepository.findAllByDiscriminatorOrderByPriceDesc(discriminator,pageFound(page));
    }

    private Page<BaseEntity> findAllByDiscriminatorOrderByNameAsc(String discriminator, int page) {
        return baseJewelryRepository.findAllByDiscriminatorOrderByNameAsc(discriminator,pageFound(page));
    }
    private Page<BaseEntity> findAllByDiscriminatorOrderByNameDesc(String discriminator, int page) {
        return baseJewelryRepository.findAllByDiscriminatorOrderByNameDesc(discriminator,pageFound(page));
    }
    private PageRequest pageFound(int page) { return PageRequest.of(page - 1,MAXIMUM_PAGE_ITEMS); }

    /**
 -------------------------------------------------------------------------------------------------------------------------------------------------------
  */

}

package com.example.jewelryspringapplication.Controllers;

import com.example.jewelryspringapplication.Models.Jewelries.BaseEntity;
import com.example.jewelryspringapplication.Models.Jewelries.Image;
import com.example.jewelryspringapplication.Services.JewelryServices.BaseJewelryService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
@RequestMapping("/products")
public class ProductsController {

    private final BaseJewelryService baseJewelryService;

    @Autowired
    public ProductsController(BaseJewelryService baseJewelryService) {
        this.baseJewelryService = baseJewelryService;
    }

    @GetMapping
    public ModelAndView productsPage(@RequestParam(name = "page", defaultValue = "1") int page,@RequestParam(required = false)
    String sortType,@RequestParam(required = false) String jewelryType,@RequestParam(required = false) String keyWords, ModelAndView mv) {

        Page<BaseEntity> jewelryPage = this.baseJewelryService.findItems(page,sortType,jewelryType,keyWords);

        mv.setViewName("product");
        mv.addObject("totalItems",jewelryPage.getTotalElements());
        mv.addObject("jewelries", jewelryPage.getContent());
        mv.addObject("currentPage", page);
        mv.addObject("totalPages", jewelryPage.getTotalPages());
        mv.addObject("sortType",sortType);
        mv.addObject("jewelryType",jewelryType);
        return mv;
    }

    @GetMapping("/detail/{discriminator}/{id}")
    public ModelAndView productDetails(@PathVariable Long id, @PathVariable String discriminator,ModelAndView mv) {

        BaseEntity jewelry = this.baseJewelryService.findByIdAndDiscriminator(id, discriminator);

        List<String> imagePaths = jewelry.getImagesPath().stream().map(Image::getPath).toList();

        List<BaseEntity> commonJewelries = this.baseJewelryService.findCommonJewelries(discriminator,jewelry);

        mv.addObject("jewelry",jewelry);

        mv.addObject("commonJewelries",commonJewelries);
        mv.addObject("paths",imagePaths);

        mv.setViewName("detail");

        return mv;
    }

    @GetMapping("/detail/cart")
    public ModelAndView cart() {
        return new ModelAndView("cart");
    }

}

package com.example.jewelryspringapplication.Controllers;

import com.example.jewelryspringapplication.Models.Jewelries.BaseEntity;
import com.example.jewelryspringapplication.Services.JewelryServices.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class BaseController {

    private final RingService ringService;
    private final BraceletService braceletService;
    private final EarringService earringService;
    private final PendantService pendantService;
    private final BaseJewelryService baseJewelryService;
    private final GemstoneService gemstoneService;

    @Autowired
    public BaseController(RingService ringService, BraceletService braceletService, EarringService earringService, PendantService pendantService, BaseJewelryService baseJewelryService, GemstoneService gemstoneService) {
        this.ringService = ringService;
        this.braceletService = braceletService;
        this.earringService = earringService;
        this.pendantService = pendantService;
        this.baseJewelryService = baseJewelryService;
        this.gemstoneService = gemstoneService;
    }

    @GetMapping("/change-language")
    public ModelAndView changeLanguage(@RequestParam String lang) {
       return new ModelAndView("redirect:/");
    }

    @GetMapping("/")
    public ModelAndView homePage(ModelAndView mv) {

        List<BaseEntity> rings = ringService.randomizeItems(5);
        List<BaseEntity> pendants = pendantService.randomizeItems(5);
        List<BaseEntity> bracelets = braceletService.randomizeItems(5);
        List<BaseEntity> earrings = earringService.randomizeItems(5);
        List<BaseEntity> gemstones = gemstoneService.randomizeItems(5);

        List<BaseEntity> randomizedJewelries = this.baseJewelryService.
        randomizeItems(5, Stream.of(rings, pendants, bracelets, earrings,gemstones).flatMap(List::stream).collect(Collectors.toList()));

        mv.setViewName("index");
        mv.addObject("rings",ringService.randomizeItems(3));
        mv.addObject("bracelets",braceletService.randomizeItems(3));
        mv.addObject("earrings",earringService.randomizeItems(3));
        mv.addObject("pendants",pendantService.randomizeItems(3));
        mv.addObject("gemstones",gemstoneService.randomizeItems(3));
        mv.addObject("randomizedRings",rings);
        mv.addObject("randomizedPendants",pendants);
        mv.addObject("randomizedEarrings",earrings);
        mv.addObject("randomizedBracelets",bracelets);
        mv.addObject("randomizedJewelries",randomizedJewelries);
        mv.addObject("randomizedGemstones",gemstones);
        return mv;
    }

    @GetMapping("/aboutUs/faq")
    public ModelAndView FaQ() {
        return new ModelAndView("FaQ");
    }

}

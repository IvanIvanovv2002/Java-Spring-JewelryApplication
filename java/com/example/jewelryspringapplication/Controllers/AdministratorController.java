package com.example.jewelryspringapplication.Controllers;

import com.example.jewelryspringapplication.Models.Jewelries.BaseEntity;
import com.example.jewelryspringapplication.Models.Users.UserOrder;
import com.example.jewelryspringapplication.Services.JewelryServices.BaseJewelryService;
import com.example.jewelryspringapplication.Services.JewelryServices.JewelryFactoryService;
import com.example.jewelryspringapplication.Services.OrderService.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
@Secured("ROLE_OWNER")
public class AdministratorController {
    private final JewelryFactoryService jewelryService;
    private final BaseJewelryService baseJewelryService;
    private final OrderService orderService;

    @Autowired
    public AdministratorController(JewelryFactoryService jewelryService, BaseJewelryService baseJewelryService, OrderService orderService) {
        this.jewelryService = jewelryService;
        this.baseJewelryService = baseJewelryService;
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public ModelAndView orders(ModelAndView mv) {
        mv.addObject("service",this.orderService);
        mv.setViewName("orders");
        return mv;
    }

    @PatchMapping("/orders/accept/{id}")
    public ModelAndView acceptOrder(@PathVariable Long id,RedirectAttributes redirectAttr,ModelAndView mv) {
        UserOrder order = this.orderService.findById(id);
        this.orderService.acceptOrder(order);
        mv.setViewName("redirect:/admin/orders");
        redirectAttr.addFlashAttribute("acceptedOrder",true);
        return mv;
    }

    @PatchMapping("/orders/remove/{id}")
    public ModelAndView removeOrder(@PathVariable Long id, ModelAndView mv,RedirectAttributes redirectAttr) {
        UserOrder order = this.orderService.findById(id);
        this.orderService.removeOrder(order);
        mv.setViewName("redirect:/admin/orders");
        redirectAttr.addFlashAttribute("removedOrder",true);
        return mv;
    }

    @PostMapping("/upload")
    public ModelAndView uploadStaticPicture(MultipartFile file) throws IOException {
        return new ModelAndView("redirect:/");
    }

    @PostMapping("/product/upload")
    public ModelAndView uploadProduct(String name, @RequestParam(required = false) Double price , String gemstone, @RequestParam(defaultValue = "false")
    Boolean inStock, String type, @RequestParam(required = false) String gemstoneOrigin,@RequestParam String keyWords,
    MultipartFile mainPicturePath, List<MultipartFile> images, @RequestParam(required = false) MultipartFile video, String description) throws IOException {

        jewelryService.createJewelry(name, price,gemstone,type,gemstoneOrigin,keyWords, inStock, mainPicturePath, images, video, description);

        return new ModelAndView("redirect:/");
    }

    @PatchMapping("/product/changeName")
    public ModelAndView changeProductName(@RequestParam String newName, @RequestParam Long productId, RedirectAttributes redirectAttr) {
        BaseEntity product = this.baseJewelryService.findById(productId);
        this.baseJewelryService.changeProductName(newName, product);
        redirectAttr.addFlashAttribute("nameChanged", true);
        return new ModelAndView("redirect:/products/detail/" + product.getDiscriminator() + "/" + product.getId());
    }

    @PatchMapping("/product/changeDescription")
    public ModelAndView changeProductDescription(@RequestParam String newDescription, @RequestParam Long productId, RedirectAttributes redirectAttr) {
        BaseEntity product = this.baseJewelryService.findById(productId);
        this.baseJewelryService.changeProductDescription(newDescription, product);
        redirectAttr.addFlashAttribute("descriptionChanged", true);
        return new ModelAndView("redirect:/products/detail/" + product.getDiscriminator() + "/" + product.getId());
    }

    @PatchMapping("/product/changePrice")
    public ModelAndView changeProductPrice(@RequestParam Double newPrice, @RequestParam Long productId, RedirectAttributes redirectAttr) {
        BaseEntity product = this.baseJewelryService.findById(productId);
        this.baseJewelryService.changeProductPrice(newPrice, product);
        redirectAttr.addFlashAttribute("priceChanged", true);
        return new ModelAndView("redirect:/products/detail/" + product.getDiscriminator() + "/" + product.getId());
    }

    @PatchMapping("/product/stockChange")
    public ModelAndView changeProductStock(@RequestParam String stock, @RequestParam Long productId, RedirectAttributes redirectAttr) {
        BaseEntity product = this.baseJewelryService.findById(productId);
        this.baseJewelryService.changeProductStock(stock, product);
        redirectAttr.addFlashAttribute("stockChanged", true);
        return new ModelAndView("redirect:/products/detail/" + product.getDiscriminator() + "/" + product.getId());
    }

    @RequestMapping("/remove/item/{id}")
    public ModelAndView removeItem(@PathVariable("id") Long id,RedirectAttributes redirectAttributes) {
        BaseEntity entity = this.baseJewelryService.findById(id);
        this.baseJewelryService.removeItem(entity);

        redirectAttributes.addFlashAttribute("itemDeleted",true);
        return new ModelAndView("redirect:/");
    }
}

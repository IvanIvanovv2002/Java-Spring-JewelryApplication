package com.example.jewelryspringapplication.Controllers;


import com.example.jewelryspringapplication.Services.JewelryServices.BaseJewelryService;
import com.example.jewelryspringapplication.Models.Jewelries.BaseEntity;
import com.example.jewelryspringapplication.Models.Users.User;
import com.example.jewelryspringapplication.Services.AuthenticationService.UserService;
import com.example.jewelryspringapplication.Services.OrderService.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.Arrays;
import java.util.Objects;

import static com.example.jewelryspringapplication.Constants.BasicConstants.DELIVERY_PRICE;

@Controller
@PreAuthorize("isAuthenticated()")
public class SecuredController {
    private final UserService userService;
    private final OrderService orderService;
    private final BaseJewelryService baseJewelryService;

    public SecuredController(UserService userService, OrderService orderService, BaseJewelryService baseJewelryService) {
        this.userService = userService;
        this.orderService = orderService;
        this.baseJewelryService = baseJewelryService;
    }

    @GetMapping("/cart")
    public ModelAndView cartPage(ModelAndView mv, Principal principal, @RequestParam(name = "page", defaultValue = "1") int page) {
        Page<BaseEntity> cartItems = this.userService.getCartItems(principal,page);
        mv.setViewName("cart");
        mv.addObject("items", cartItems.getContent());
        mv.addObject("pages", page);
        mv.addObject("totalPages", cartItems.getTotalPages());
        mv.addObject("totalPrice",cartItems.getContent().stream().mapToDouble(BaseEntity::getPrice).sum());
        return mv;
    }

    @PostMapping("/cart/checkout")
    public ModelAndView checkout(@RequestParam Double subtotalPrice, String comments, ModelAndView mv, Authentication authentication) throws IOException {

        double totalPrice;

        if (user(authentication).getIsSubscribed()) {
            totalPrice = (subtotalPrice - (subtotalPrice * 0.05)) + DELIVERY_PRICE;
            mv.addObject("subscribedUser",true);
        } else {
            totalPrice = subtotalPrice + DELIVERY_PRICE;
        }

        mv.addObject("subtotalPrice", subtotalPrice);
        mv.addObject("comments",comments);
        mv.addObject("totalPrice", totalPrice);
        mv.setViewName("checkout");
        return mv;
    }


    @PostMapping("/cart/checkout/order")
    public ModelAndView makeOrder(String firstName, String lastName, String city, String phoneNumber, String address, String email,
     String delivery, String comments, Double totalPrice, RedirectAttributes redirectAttr,HttpServletRequest request) {

      String itemsCookie = Arrays.stream(request.getCookies()).filter(cookie -> "items".equals(cookie.getName())).findFirst().get().getValue();
      try {
          this.orderService.makeOrder(firstName, lastName, city, phoneNumber, address, email, delivery,comments,totalPrice,URLDecoder.decode(itemsCookie,StandardCharsets.UTF_8));
      } catch (Exception e) {
            e.printStackTrace();
       }

      redirectAttr.addFlashAttribute("successfullyOrdered",true);
      return new ModelAndView("redirect:/");
    }

    @GetMapping("/profile")
    public ModelAndView profile(Authentication authentication,ModelAndView mv) {
        User currentUser = user(authentication);
        mv.setViewName("profile");
        return mv.addObject("user",currentUser);
    }

    @GetMapping("/add/item/cart")
    public ModelAndView addToCart(@RequestParam("discriminator") String discriminator, @RequestParam("jewelryId") Long jewelryId,
      Principal principal, RedirectAttributes redirectAttr ) {
        this.userService.addItemToCart(discriminator,jewelryId,principal);
        redirectAttr.addFlashAttribute("itemAdded",true);
        return new ModelAndView("redirect:/products");
    }

    @GetMapping("/add/item/wishlist")
    public ModelAndView addToWishlist(@RequestParam("discriminator") String discriminator, @RequestParam("jewelryId") Long jewelryId,
    Principal principal, RedirectAttributes redirectAttr ) {
        this.userService.addItemToWishlist(discriminator,jewelryId,principal);
        redirectAttr.addFlashAttribute("itemAdded",true);
        return new ModelAndView("redirect:/products");
    }

    @PatchMapping("/newsletter/subscribe")
    public ModelAndView subscribeToNewsletters(String email,Authentication authentication,RedirectAttributes redirectAttr) {
        this.userService.checkForErrorsDuringSubscription(email,user(authentication));
        redirectAttr.addFlashAttribute("successfullySubscribed",true);
        return new ModelAndView("redirect:/");
    }

    @PatchMapping("/profile/changePassword")
    public ModelAndView changePassword(String oldPassword,String newPassword,Authentication authentication,RedirectAttributes redirectAttr) {
        this.userService.checkForErrorsDuringUpdate(oldPassword,newPassword, user(authentication));
        this.userService.changePassword(newPassword,user(authentication));
        redirectAttr.addFlashAttribute("changedPassword",true);
        return new ModelAndView("redirect:/profile");
    }

    @PatchMapping("/profile/wishlist/removeItem")
    public ModelAndView removeItemFromWishlist(@RequestParam Long id,Authentication authentication,ModelAndView mv,RedirectAttributes redirectAttr) {
        user(authentication).getWishlist().remove(this.baseJewelryService.findById(id));
        this.userService.save(user(authentication));
        mv.setViewName("redirect:/profile");
        redirectAttr.addFlashAttribute("removedItemWishlist",true);
        return mv;
    }

    @DeleteMapping("/profile/cart/removeItem/{itemId}")
    public ModelAndView removeCartItem(@PathVariable Long itemId, Authentication authentication,RedirectAttributes redirectAttr,ModelAndView mv) {
        BaseEntity product = user(authentication).getCartItems().stream().filter(item -> Objects.equals(item.getId(), itemId)).findFirst().orElse(null);
        user(authentication).getCartItems().remove(product);
        this.userService.save(user(authentication));
        mv.setViewName("redirect:/cart");
        redirectAttr.addFlashAttribute("successfullyRemovedItem",true);
        return mv;
    }

    @ModelAttribute
    public User user(Authentication authentication) {
        return this.userService.findUserByEmail(authentication.getName()).orElseThrow();
    }
}

package com.example.jewelryspringapplication.Services.OrderService;

import com.example.jewelryspringapplication.Models.Jewelries.BaseEntity;
import com.example.jewelryspringapplication.Services.JewelryServices.BaseJewelryService;
import com.example.jewelryspringapplication.Models.Users.UserOrder;
import com.example.jewelryspringapplication.Repositories.UserOrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class OrderService {

    private final UserOrderRepository userOrderRepository;
    private final BaseJewelryService baseJewelryService;
    private final ObjectMapper objectMapper;

    @Autowired
    public OrderService(UserOrderRepository userOrderRepository, BaseJewelryService baseJewelryService, ObjectMapper objectMapper) {
        this.userOrderRepository = userOrderRepository;
        this.baseJewelryService = baseJewelryService;
        this.objectMapper = objectMapper;
    }

    public void makeOrder(String firstName, String lastName, String city, String phoneNumber, String address, String email,
        String delivery, String message, Double totalPrice,String cookie) {
        this.userOrderRepository.save(new UserOrder(firstName,lastName,city,delivery,address,email,phoneNumber,message,totalPrice,cookie));
    }

    public Map<BaseEntity,Integer> extractItems(Long id) throws JsonProcessingException {

        Map<BaseEntity, Integer> productsToBeReturned = new HashMap<>();

        String jsonProducts = this.findById(id).getJsonProducts();

        Map<Long,Integer> mappedProducts  = this.objectMapper.readValue(jsonProducts, new TypeReference<>() { } );

        for (Map.Entry<Long, Integer> currentMap : mappedProducts.entrySet()) {
            productsToBeReturned.put(this.baseJewelryService.findById(currentMap.getKey()),currentMap.getValue());
        }
        return productsToBeReturned;
    }
    public List<UserOrder> findAllOrders() {
        return this.userOrderRepository.findAll();
    }

    public UserOrder findById(Long id) {
      return this.userOrderRepository.findById(id).orElseThrow();
    }

    public void acceptOrder(UserOrder order) {
        this.userOrderRepository.delete(order);
    }

    public void removeOrder(UserOrder order) {
        this.userOrderRepository.delete(order);
    }

}

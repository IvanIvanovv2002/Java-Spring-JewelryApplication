package com.example.jewelryspringapplication.Pointcuts;

import com.example.jewelryspringapplication.Models.Users.UserOrder;
import com.example.jewelryspringapplication.Services.EmailService.EmailServiceImpl;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class OrderAcceptedPointcut {

    private final EmailServiceImpl emailService;

    public OrderAcceptedPointcut(EmailServiceImpl emailService) {
        this.emailService = emailService;
    }

    @Pointcut(value = "execution(* com.example.jewelryspringapplication.Services.OrderService.OrderService.acceptOrder(..)) && args(order)", argNames = "order")
    public void orderAcceptedPointcut(UserOrder order) { }

    @After(value = "orderAcceptedPointcut(order)", argNames = "order")
    public void sendEmailAfterAcceptedOrder(UserOrder order) {
        this.emailService.sendEmailAfterVerifiedOrder(order.getEmail());
    }

}

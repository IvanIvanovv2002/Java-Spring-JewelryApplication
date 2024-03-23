package com.example.jewelryspringapplication.Repositories;

import com.example.jewelryspringapplication.Models.Users.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserOrderRepository extends JpaRepository<UserOrder,Long> {

}

package com.order.order_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.order.order_management.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer>{
     
}

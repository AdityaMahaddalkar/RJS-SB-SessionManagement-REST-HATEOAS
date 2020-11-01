package com.restdemo.restfulservice.repository;

import com.restdemo.restfulservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

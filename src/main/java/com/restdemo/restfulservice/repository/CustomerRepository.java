package com.restdemo.restfulservice.repository;

import com.restdemo.restfulservice.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
}

package com.restdemo.restfulservice.repository;

import com.restdemo.restfulservice.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}

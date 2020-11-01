package com.restdemo.restfulservice.smoke;

import com.restdemo.restfulservice.controller.CustomerController;
import com.restdemo.restfulservice.controller.EmployeeController;
import com.restdemo.restfulservice.controller.OrderController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
public class SmokeTest {

    @Autowired
    private CustomerController customerController;
    @Autowired
    private OrderController orderController;
    @Autowired
    private EmployeeController employeeController;

    @Test
    void contextLoads() {
        Assert.notNull(customerController);
        Assert.notNull(orderController);
        Assert.notNull(employeeController);
    }
}

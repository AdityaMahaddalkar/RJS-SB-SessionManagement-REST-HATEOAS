package com.restdemo.restfulservice.database;

import com.restdemo.restfulservice.entity.Customer;
import com.restdemo.restfulservice.entity.Employee;
import com.restdemo.restfulservice.entity.Order;
import com.restdemo.restfulservice.entity.Status;
import com.restdemo.restfulservice.repository.CustomerRepository;
import com.restdemo.restfulservice.repository.EmployeeRepository;
import com.restdemo.restfulservice.repository.OrderRepository;
import com.restdemo.restfulservice.security.entity.User;
import com.restdemo.restfulservice.security.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initEmployeesDatabase(EmployeeRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new Employee("Bilbo Baggins", "burglar")));
            log.info("Preloading " + repository.save(new Employee("Frodo Baggins", "thief")));
        };
    }

    @Bean
    CommandLineRunner initOrdersDatabase(OrderRepository orderRepository) {
        return args -> {
            log.info("Preloading " + orderRepository.save(new Order("Cereal", Status.IN_PROGRESS)));
            log.info("Preloading " + orderRepository.save(new Order("Fish", Status.IN_PROGRESS)));
            log.info("Preloading " + orderRepository.save(new Order("Bread", Status.IN_PROGRESS)));
            log.info("Preloading " + orderRepository.save(new Order("Eggs", Status.IN_PROGRESS)));
            log.info("Preloading " + orderRepository.save(new Order("Milk", Status.IN_PROGRESS)));
            log.info("Preloading " + orderRepository.save(new Order("Apples", Status.IN_PROGRESS)));

        };
    }

    @Bean
    CommandLineRunner initCustomersDatabase(CustomerRepository customerRepository) {
        return args -> {
            log.info("Preloading " + customerRepository.save(new Customer("Raymond", "Holt")));
            log.info("Preloading " + customerRepository.save(new Customer("Jake", "Peralta")));
            log.info("Preloading " + customerRepository.save(new Customer("Terry", "Crews")));
            log.info("Preloading " + customerRepository.save(new Customer("Amy", "Santiago")));
            log.info("Preloading " + customerRepository.save(new Customer("Charles", "Boyle")));
        };
    }

    @Bean
    CommandLineRunner initUsersDatabase(UserRepository userRepository) {
        return args -> {
            log.info("Preloading " + userRepository.save(new User("admin1", "adminpass", "admin@email.com", "ADMIN")));
            log.info("Preloading " + userRepository.save(new User("user1", "userpass", "user@email.com", "USER")));
        };
    }
}

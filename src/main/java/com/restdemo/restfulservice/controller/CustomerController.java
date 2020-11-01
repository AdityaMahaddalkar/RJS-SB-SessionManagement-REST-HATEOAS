package com.restdemo.restfulservice.controller;

import com.restdemo.restfulservice.assembler.CustomerModelAssembler;
import com.restdemo.restfulservice.entity.Customer;
import com.restdemo.restfulservice.exception.CustomerNotFoundException;
import com.restdemo.restfulservice.repository.CustomerRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CustomerController {

    private final CustomerRepository customerRepository;
    private final CustomerModelAssembler customerModelAssembler;

    public CustomerController(CustomerRepository customerRepository, CustomerModelAssembler customerModelAssembler) {
        this.customerRepository = customerRepository;
        this.customerModelAssembler = customerModelAssembler;
    }

    @GetMapping("/customers")
    public CollectionModel<EntityModel<Customer>> all() {
        List<EntityModel<Customer>> entityModelList = customerRepository.findAll()
                .stream()
                .map(customerModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(entityModelList);
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<?> one(@PathVariable Long id) {
        Customer customer;
        try {
            customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
        } catch (CustomerNotFoundException customerNotFoundException) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(customerNotFoundException.getMessage());
        }
        return ResponseEntity.ok(customerModelAssembler.toModel(customer));
    }

    @PostMapping("/customers")
    public ResponseEntity newOrder(@RequestBody Customer customer) {
        Customer savedCustomer;
        try {
            savedCustomer = customerRepository.save(customer);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(customerModelAssembler.toModel(savedCustomer));
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity deleteCustomer(@PathVariable Long id) {
        try {
            customerRepository.deleteById(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok("Customer deleted with id " + id);
    }

}

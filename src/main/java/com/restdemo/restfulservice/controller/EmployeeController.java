package com.restdemo.restfulservice.controller;

import com.restdemo.restfulservice.assembler.EmployeeModelAssembler;
import com.restdemo.restfulservice.entity.Employee;
import com.restdemo.restfulservice.exception.EmployeeNotFoundException;
import com.restdemo.restfulservice.repository.EmployeeRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class EmployeeController {

    private final EmployeeRepository employeeRepository;
    private final EmployeeModelAssembler employeeModelAssembler;

    public EmployeeController(EmployeeRepository employeeRepository, EmployeeModelAssembler employeeModelAssembler) {
        this.employeeRepository = employeeRepository;
        this.employeeModelAssembler = employeeModelAssembler;
    }

    @CrossOrigin
    @GetMapping("/employees")
    public CollectionModel<EntityModel<Employee>> all() {

        List<EntityModel<Employee>> entityModelList = employeeRepository.findAll()
                .stream()
                .map(employeeModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(entityModelList);
    }

    @CrossOrigin
    @PostMapping("/employees")
    public ResponseEntity newEmployee(@RequestBody Employee newEmployee) {

        ResponseEntity responseEntity;
        Employee savedEmployee;
        try {
            savedEmployee = employeeRepository.save(newEmployee);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeModelAssembler.toModel(savedEmployee));
    }

    @CrossOrigin
    @GetMapping("/employees/{id}")
    public ResponseEntity one(@PathVariable Long id) {

        Employee employee;
        try {
            employee = employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        } catch (EmployeeNotFoundException employeeNotFoundException) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(employeeNotFoundException);
        }
        return ResponseEntity.ok(employeeModelAssembler.toModel(employee));
    }

    @CrossOrigin
    @PutMapping("/employees/{id}")
    public ResponseEntity replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {

        Employee savedEmployee;
        try {
            savedEmployee = employeeRepository.findById(id).map(employee -> {
                employee.setName(newEmployee.getName());
                employee.setRole(newEmployee.getRole());
                return employeeRepository.save(employee);
            }).orElseGet(() -> {
                newEmployee.setId(id);
                return employeeRepository.save(newEmployee);
            });
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }

        return ResponseEntity.ok(employeeModelAssembler.toModel(savedEmployee));
    }

    @CrossOrigin
    @DeleteMapping("/employees/{id}")
    public ResponseEntity deleteEmployee(@PathVariable Long id) {

        try {
            employeeRepository.deleteById(id);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
        return ResponseEntity.ok().body("Employee deleted with id " + id);

    }
}

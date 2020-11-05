package ru.isu.i2kiselev.rxordermanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.isu.i2kiselev.rxordermanager.model.Employee;
import ru.isu.i2kiselev.rxordermanager.repository.EmployeeRepository;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Mono<Employee> saveEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    public Flux<Employee> findAll(){
        return employeeRepository.findAll();
    }

    public Mono<Employee> findById(Integer id){
        return employeeRepository.findById(id);
    }
}

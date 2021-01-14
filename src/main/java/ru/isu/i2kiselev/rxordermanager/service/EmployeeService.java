package ru.isu.i2kiselev.rxordermanager.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.isu.i2kiselev.rxordermanager.model.Employee;
import ru.isu.i2kiselev.rxordermanager.model.Task;
import ru.isu.i2kiselev.rxordermanager.repository.EmployeeRepository;

/**
 * Singleton-service for CRUD-operations with Employee class
 * @version 0.1
 * @author Ilya Kiselev
 */

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Mono<Employee> saveEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    public Flux<Employee> findAll(){
        return employeeRepository.findAll();
    }

    public Mono<Employee> findById(Integer id){
        return employeeRepository.findById(id);
    }

    public Flux<Employee> findByTask(Integer id) {
        return employeeRepository.findAllByTaskId(id);
    }

    public Mono<Employee> addTaskToEmployee(Employee employee, Task task, Integer taskEstimate){
           employee.addTaskEstimate(task, taskEstimate);
           return employeeRepository.save(employee);

    }

}

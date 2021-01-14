package ru.isu.i2kiselev.rxordermanager.service;

import lombok.extern.log4j.Log4j2;
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
@Log4j2
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Mono<Employee> saveEmployee(Employee employee){
        log.info("Saved employee with id {}", employee::getUsername);
        return employeeRepository.save(employee);
    }

    public Flux<Employee> findAll(){
        log.info("Returned all employees");
        return employeeRepository.findAll();
    }

    public Mono<Employee> findById(Integer id){
        log.info("Returned employee with id {}", id);
        return employeeRepository.findById(id);
    }

    public Flux<Employee> findByTask(Integer id) {
        log.info("Returned employee by task with id {}", id);
        return employeeRepository.findAllByTaskId(id);
    }

    public Mono<Employee> addTaskToEmployee(Employee employee, Task task, Integer taskEstimate){
           log.info("Added task #{} with completion estimate of {} to employee #{}", task::getId, taskEstimate::intValue, employee::getId);
           employee.addTaskEstimate(task, taskEstimate);
           return employeeRepository.save(employee);

    }

}

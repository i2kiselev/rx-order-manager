package ru.isu.i2kiselev.rxordermanager.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.isu.i2kiselev.rxordermanager.model.Employee;
import ru.isu.i2kiselev.rxordermanager.repository.EmployeeRepository;

/**
 * Singleton-service for operations with Employee class
 * @version 0.2
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

    public Mono<Void> addTaskEstimate(Integer employeeId, Integer taskId, Integer taskEstimate){
        log.info("Added task #{} estimate of {} units to employee #{} ", taskId, taskEstimate, employeeId);
        return employeeRepository.addTaskEstimateToEmployee(employeeId,taskId,taskEstimate);
    }

    public Mono<Void> updateTaskEstimate(Integer employeeId, Integer taskId, Integer taskEstimate){
        log.info("Update task #{} estimate of {} units to employee #{} ", taskId, taskEstimate, employeeId);
        return employeeRepository.updateTaskEstimateOfEmployee(employeeId,taskId,taskEstimate);
    }

    public Mono<Void> removeTaskEstimate(Integer employeeId, Integer taskId){
        log.info("Removed task #{} estimate of employee #{} ", taskId,  employeeId);
        return employeeRepository.removeTaskEstimateToEmployee(employeeId,taskId);
    }


}

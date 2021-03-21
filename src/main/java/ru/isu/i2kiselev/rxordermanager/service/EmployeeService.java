package ru.isu.i2kiselev.rxordermanager.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.isu.i2kiselev.rxordermanager.model.Employee;
import ru.isu.i2kiselev.rxordermanager.model.TaskEstimate;
import ru.isu.i2kiselev.rxordermanager.repository.EmployeeRepository;

/**
 * Singleton-service for operations with Employee class
 * @author Ilya Kiselev
 */

@Service
@Log4j2
public class EmployeeService implements ReactiveUserDetailsService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Mono<Employee> saveEmployee(Employee employee){
        return employeeRepository.save(employee).doOnNext(x->log.debug("Saved employee with id {}", x::getId));
    }

    public Mono<Void> deleteEmployeeById(Integer employeeId){
        log.debug("Deleted employee with id {}", employeeId);
        return employeeRepository.deleteById(employeeId);
    }

    public Flux<Employee> findAll(){
        log.debug("Returned all employees");
        return employeeRepository.findAll().filter(x-> !x.getRole().equals("ROLE_ADMIN"));
    }
    
    public Mono<Employee> findById(Integer id){
        log.debug("Returned employee with id {}", id);
        return employeeRepository.findById(id);
    }

    public Mono<UserDetails> findByUsername(String username){
        log.debug("Returned employee with name {}", username);
        return employeeRepository.findByUsername(username).cast(UserDetails.class);
    }

    public Mono<Boolean> isRegistered(String username){
        return employeeRepository.findByUsername(username).hasElement();
    }

    public Mono<Employee> getCurrentEmployee(){
        return ReactiveSecurityContextHolder.getContext().map(SecurityContext::getAuthentication).map(Authentication::getPrincipal).cast(Employee.class);
    }

    public Flux<Employee> findAllByTaskId(Integer id) {
        log.debug("Returned employee by task with id {}", id);
        return employeeRepository.findAllByTaskId(id);
    }

    public Flux<Employee> findAllByTaskQueueId(Integer taskQueueId) {
        log.debug("Returned all employees by taskQueue {}", taskQueueId);
        return employeeRepository.findAllByTaskQueueId(taskQueueId);
    }
    
    public Flux<Employee> findAllAssignedToOrder(Integer orderId){
        log.debug("Returned all employees assigned to order {}", orderId);
        return employeeRepository.findAllAssignedToOrder(orderId);
    }

    public Mono<Integer> addTaskEstimate(Integer employeeId, Integer taskId, Integer taskEstimate){
        log.debug("Added task #{} estimate of {} units to employee #{} ", taskId, taskEstimate, employeeId);
        return employeeRepository.addTaskEstimateToEmployee(employeeId,taskId,taskEstimate);
    }

    public Mono<Integer> addTaskEstimate(TaskEstimate taskEstimate){
        log.debug("Added task #{} estimate of {} units to employee #{} ", taskEstimate.getTaskId(), taskEstimate.getEstimate(), taskEstimate.getEmployeeId());
        return employeeRepository.addTaskEstimateToEmployee(taskEstimate.getEmployeeId(), taskEstimate.getTaskId(), taskEstimate.getEstimate() );
    }

    public Mono<Integer> updateTaskEstimate(Integer employeeId, Integer taskId, Integer taskEstimate){
        log.debug("Update task #{} estimate of {} units to employee #{} ", taskId, taskEstimate, employeeId);
        return employeeRepository.updateTaskEstimateOfEmployee(employeeId,taskId,taskEstimate);
    }

    public Mono<Integer> deleteTaskEstimate(Integer employeeId, Integer taskId){
        log.debug("Removed task #{} estimate of employee #{} ", taskId,  employeeId);
        return employeeRepository.removeTaskEstimateToEmployee(employeeId,taskId);
    }



}

package ru.isu.i2kiselev.rxordermanager.controller;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.isu.i2kiselev.rxordermanager.model.Employee;
import ru.isu.i2kiselev.rxordermanager.service.EmployeeService;

import java.util.ArrayList;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/save")
    public Mono<Employee> saveEmployeeTest(){
        Employee employee = new Employee();
        employee.setUsername("ilusha");
        ArrayList<Integer> tasks = new ArrayList<>();
        return employeeService.saveEmployee(employee);
    }

    @GetMapping("/all")
    public Flux<Employee> findAll(){
        return employeeService.findAll();
    }

    @GetMapping("/all-by-task/{taskId}")
    public Flux<Employee> findAllByTask(@PathVariable Integer taskId){
        return employeeService.findByTask(taskId);
    }

    @PostMapping("/{employeeId}/task/{taskId}/estimate/{estimate}")
    public Mono<Void> addTaskEstimate(@PathVariable Integer employeeId, @PathVariable Integer taskId, @PathVariable Integer estimate){
            return employeeService.addTaskEstimate(employeeId,taskId,estimate);
    }

    @PutMapping("/{employeeId}/task/{taskId}/estimate/{estimate}")
    public Mono<Void> updateTaskEstimate(@PathVariable Integer employeeId, @PathVariable Integer taskId, @PathVariable Integer estimate){
        return employeeService.updateTaskEstimate(employeeId,taskId,estimate);
    }

    @DeleteMapping("/{employeeId}/task/{taskId}/")
    public Mono<Void> removeTaskEstimate(@PathVariable Integer employeeId, @PathVariable Integer taskId){
        return employeeService.removeTaskEstimate(employeeId,taskId);
    }
}

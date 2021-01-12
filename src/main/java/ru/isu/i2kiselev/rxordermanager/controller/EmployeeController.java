package ru.isu.i2kiselev.rxordermanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.isu.i2kiselev.rxordermanager.model.Employee;
import ru.isu.i2kiselev.rxordermanager.service.EmployeeService;

import java.util.ArrayList;
import java.util.Arrays;

@RestController
@RequestMapping("/")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/save")
    public Mono<Employee> saveEmployeeTest(){
        Employee employee = new Employee();
        employee.setUsername("ilusha");
        ArrayList<Integer> tasks = new ArrayList<>();
        employee.setTasks(Arrays.asList(1,2,3));
        employee.setEstimates(Arrays.asList(3,2,1));
        return employeeService.saveEmployee(employee);
    }

    @GetMapping("/findAll")
    public Flux<Employee> findAll(){
        return employeeService.findAll();
    }

    @GetMapping("/findAllByTaskId/{taskId}")
    public Flux<Employee> findAllByTaskId(@PathVariable Integer taskId){
        return employeeService.findByTaskId(taskId);
    }

}

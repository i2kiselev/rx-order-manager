package ru.isu.i2kiselev.rxordermanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.isu.i2kiselev.rxordermanager.model.Employee;
import ru.isu.i2kiselev.rxordermanager.model.Task;
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
        ArrayList<Task> tasks = new ArrayList<>();
        Task task  = new Task();
        task.setTaskName("taskTestName");
        tasks.add(task );
        employee.setTasks(Arrays.asList(3,2,3));
        return employeeService.saveEmployee(employee);
    }

    @GetMapping("/findAll")
    public Flux<Employee> findAll(){
        return employeeService.findAll();
    }



}

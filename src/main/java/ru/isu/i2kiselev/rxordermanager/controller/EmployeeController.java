package ru.isu.i2kiselev.rxordermanager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.isu.i2kiselev.rxordermanager.model.Employee;
import ru.isu.i2kiselev.rxordermanager.service.EmployeeService;

import java.util.ArrayList;
import java.util.Arrays;

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
        employee.setTasks(Arrays.asList(1,2,3));
        employee.setEstimates(Arrays.asList(3,2,1));
        return employeeService.saveEmployee(employee);
    }

    @GetMapping("/findAll")
    public Flux<Employee> findAll(){
        return employeeService.findAll();
    }

    @GetMapping("/findAllByTask/{taskId}")
    public Flux<Employee> findAllByTask(@PathVariable Integer taskId){
        return employeeService.findByTask(taskId);
    }

}

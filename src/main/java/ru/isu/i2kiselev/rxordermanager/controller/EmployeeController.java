package ru.isu.i2kiselev.rxordermanager.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.isu.i2kiselev.rxordermanager.model.Employee;
import ru.isu.i2kiselev.rxordermanager.service.EmployeeService;

@Controller
@RequestMapping("/employee")
@Log4j2
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public Mono<String> employees(Model model){
        model.addAttribute("employees", employeeService.findAll());
        log.info("Returned employee view");
        return Mono.just("employee");
    }

    @GetMapping("/{employeeId}")
    public Mono<String> editEmployee(@PathVariable Integer employeeId, Model model){
        model.addAttribute("emp", employeeService.findByTask(employeeId));
        return Mono.just("add-employee");
    }

    @PutMapping("/{employeeId}")
    public Mono<String> saveEditedEmployee(@PathVariable Integer employeeId, @ModelAttribute Employee employee){
        return employeeService.saveEmployee(employee).thenReturn("redirect:/employee");
    }

    @DeleteMapping("/{employeeId}")
    public Mono<String> removeEmployee(@PathVariable Integer employeeId){
        return employeeService.deleteEmployeeById(employeeId).thenReturn("employee");
    }

    @GetMapping("/add")
    public Mono<String> addEmployee(Model model){
        log.info("Returned add-employee view");
        model.addAttribute("employee", new Employee());
        return Mono.just("add-employee");
    }

    @PostMapping("/add")
    public Mono<String> addEmployee(@ModelAttribute("employee") Employee employee){
        return employeeService.saveEmployee(employee).thenReturn("redirect:/employee/");
    }



}

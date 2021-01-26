package ru.isu.i2kiselev.rxordermanager.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.isu.i2kiselev.rxordermanager.model.Employee;
import ru.isu.i2kiselev.rxordermanager.service.EmployeeService;
import ru.isu.i2kiselev.rxordermanager.service.TaskService;

/**
 * Employee controller. Allows to add/delete/update employees
 * @version 0.1
 * @author Ilya Kiselev
 */

@Controller
@RequestMapping("/employee")
@Log4j2
public class EmployeeController {

    private final EmployeeService employeeService;

    private final TaskService taskService;

    public EmployeeController(EmployeeService employeeService, TaskService taskService) {
        this.employeeService = employeeService;
        this.taskService = taskService;
    }

    @GetMapping("/")
    public Mono<String> employees(Model model){
        model.addAttribute("employees", employeeService.findAll());
        log.info("Returned employees view");
        return Mono.just("employees");
    }
    @GetMapping("")
    public Mono<String> redirect(Model model){
        return employees(model);
    }

    @GetMapping("/add")
    public Mono<String> addEmployee(Model model){
        log.info("Returned add-employee view");
        model.addAttribute("employee", new Employee());
        return Mono.just("add-employee");
    }

    @PostMapping("/add")
    public Mono<String> addEmployee(@ModelAttribute("employee") Employee employee, Model model){
        return employeeService.saveEmployee(employee).then(employees(model));
    }

    @GetMapping("/{employeeId}")
    public Mono<String> editEmployee(@PathVariable Integer employeeId, Model model){
        model.addAttribute("employee", employeeService.findById(employeeId));
        return Mono.just("edit-employee");
    }

    @PutMapping("/{employeeId}")
    public Mono<String> saveEditedEmployee(@PathVariable Integer employeeId, @ModelAttribute Employee employee, Model model){
        return employeeService.saveEmployee(employee).then(employees(model));
    }

    @DeleteMapping("/{employeeId}")
    public Mono<String> removeEmployee(@PathVariable Integer employeeId, Model model){
        return employeeService.deleteEmployeeById(employeeId).then(employees(model));
    }

    @GetMapping("/{employeeId}/tasks")
    public Mono<String> employeeTasks(@PathVariable Integer employeeId, Model model){
        model.addAttribute("tasks", taskService.findAllByEmployeeId(employeeId));
        model.addAttribute("employee",employeeService.findById(employeeId));
        return Mono.just("employee-tasks");
    }

    /*@GetMapping("/{employeeId}/tasks/{taskId}")
    public Mono<String> employeeTask(@PathVariable Integer employeeId, Model model, @PathVariable Integer taskId){
        model.addAttribute("tasks", taskService.findAllByEmployeeId(employeeId));
        model.addAttribute("employee",employeeService.findById(employeeId));
        model.addAttribute("task",taskService.findById(taskId));
        return Mono.just("employee-task");
    }

    @PostMapping("/{employeeId}/tasks/{taskId}")
    public Mono<String> addEmployeeTask(@ModelAttribute("task") Task task, Model model, @PathVariable Integer taskId, @PathVariable Integer employeeId){
        Integer estimate=0;
        return employeeService.addTaskEstimate(employeeId,taskId,estimate)
                            .then(taskService.save(task))
                            .thenReturn("employee-tasks");
    }

    @PutMapping("/{employeeId}/tasks/{taskId}")
    public Mono<String> updateEmployeeTask(@PathVariable Integer employeeId, Model model, @PathVariable Integer taskId){
        model.addAttribute("tasks", taskService.findAllByEmployeeId(employeeId));
        model.addAttribute("employee",employeeService.findById(employeeId));
        model.addAttribute("task",taskService.findById(taskId));
        return Mono.just("employee-tasks");
    }*/



}

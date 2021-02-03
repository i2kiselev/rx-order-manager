package ru.isu.i2kiselev.rxordermanager.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;
import ru.isu.i2kiselev.rxordermanager.model.Order;
import ru.isu.i2kiselev.rxordermanager.service.EmployeeService;
import ru.isu.i2kiselev.rxordermanager.service.OrderService;
import ru.isu.i2kiselev.rxordermanager.service.TaskService;

/**
 * ManagerController
 * @version 0.1
 * @author Ilya Kiselev
 */

@Controller
@Log4j2
@RequestMapping("/manager")
public class ManagerController {

    private final EmployeeService employeeService;

    private final TaskService taskService;

    private final OrderService orderService;

    public ManagerController(EmployeeService employeeService, TaskService taskService, OrderService orderService) {
        this.employeeService = employeeService;
        this.taskService = taskService;
        this.orderService = orderService;
    }

    @GetMapping("/order/add")
    //public Mono<String> orderForm(Model model){
    public String orderForm(Model model){
        model.addAttribute("order", new Order());
        model.addAttribute("tasks", taskService.findAll());
        log.info("ManagerController : orderForm, returned add-order view");
        return "add-order";
        //return Mono.just("add-order");
    }

    @PostMapping("/order/add")
    public Mono<String> saveOrder(@ModelAttribute("order") Order order, Model model){
        return orderService.saveFromForm(order).thenReturn("orders");
    }


}

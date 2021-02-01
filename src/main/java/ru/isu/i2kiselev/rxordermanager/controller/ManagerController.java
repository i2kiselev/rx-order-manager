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
@RequestMapping("/dashboard")
public class ManagerController {

    private EmployeeService employeeService;

    private TaskService taskService;

    private OrderService orderService;

    @GetMapping("/order/add")
    public Mono<String> orderForm(Model model){
        model.addAttribute("order", new Order());
        log.info("ManagerController : orderForm, returned add-order view");
        return Mono.just("add-order");
    }

    @PostMapping("/order/add")
    public Mono<String> saveOrder(@ModelAttribute("order") Order order, Model model){
        return orderService.save(order).thenReturn("orders");
    }


}

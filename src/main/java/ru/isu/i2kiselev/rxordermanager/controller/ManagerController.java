package ru.isu.i2kiselev.rxordermanager.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/")
    public Mono<String> index(Model model){
        model.addAttribute("orders", orderService.findAll());
        log.info("Returned tasks view");
        return Mono.just("orders");
    }

    @GetMapping("")
    public Mono<String> redirect(Model model){
        return index(model);
    }


    @GetMapping("/order/add")
    public Mono<String> orderForm(Model model){
        model.addAttribute("order", new Order());
        model.addAttribute("tasks", taskService.findAll());
        log.info("Returned add-order view");
        return Mono.just("add-order");
    }

    @PostMapping("/order/add")
    public Mono<String> saveOrder(@ModelAttribute("order") Order order){
        return orderService.saveFromForm(order).thenReturn("orders");
    }

    @PostMapping("/order/{orderId}/delete")
    public Mono<String> removeEmployee(@PathVariable Integer orderId, Model model){
        return orderService.deleteById(orderId).then(index(model));
    }

    @GetMapping("/order/{orderId}/manage")
    public Mono<String> manageOrder(@PathVariable Integer orderId, Model model){
        model.addAttribute("order", orderService.findOrderById(orderId));
        model.addAttribute("taskRecords", orderService.findAllTaskQueuesByOrderId(orderId));
        model.addAttribute("tasks", taskService.findAllByOrderId(orderId));
        return Mono.just("order");
    }

    @GetMapping("/order/{orderId}/manage/{taskQueueId}/assign")
    public Mono<String> manageOrder(@PathVariable Integer orderId, @PathVariable Integer taskQueueId, Model model){
        model.addAttribute("employees", orderService.findTaskQueueById(taskQueueId).map(x->employeeService.findAllByTaskId(x.getTaskId())));
        return Mono.just("assign-task");
    }



}

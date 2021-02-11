package ru.isu.i2kiselev.rxordermanager.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.isu.i2kiselev.rxordermanager.model.Order;
import ru.isu.i2kiselev.rxordermanager.model.Status;
import ru.isu.i2kiselev.rxordermanager.model.TaskQueue;
import ru.isu.i2kiselev.rxordermanager.service.EmployeeService;
import ru.isu.i2kiselev.rxordermanager.service.ManagerService;
import ru.isu.i2kiselev.rxordermanager.service.TaskService;

/**
 * ManagerController
 * @version 0.5
 * @author Ilya Kiselev
 */

@Controller
@Log4j2
@RequestMapping("/manager")
public class ManagerController {

    private final EmployeeService employeeService;

    private final TaskService taskService;

    private final ManagerService managerService;

    public ManagerController(EmployeeService employeeService, TaskService taskService, ManagerService managerService) {
        this.employeeService = employeeService;
        this.taskService = taskService;
        this.managerService = managerService;
    }

    @GetMapping("/")
    public Mono<String> index(Model model){
        model.addAttribute("orders", managerService.findAllOrders());
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
        return managerService.saveOrderFromForm(order)
                .thenReturn("orders");
    }

    @PostMapping("/order/{orderId}/delete")
    public Mono<String> removeEmployee(@PathVariable Integer orderId, Model model){
        return managerService.deleteAllTaskQueuesByOrderId(orderId)
                .then(managerService.deleteOrderById(orderId))
                .then(index(model));
    }

    @GetMapping("/order/{orderId}/manage")
    public String manageOrder(@PathVariable Integer orderId, Model model){
        model.addAttribute("order", managerService.findOrderById(orderId));
        model.addAttribute("taskRecords", managerService.findAllTaskQueuesByOrderId(orderId));
        model.addAttribute("tasks", taskService.findAllByOrderId(orderId));
        model.addAttribute("assignedEmployees", employeeService.findAllAssignedToOrder(orderId));
        model.addAttribute("completed", Status.COMPLETED);
        return "order";
    }

    @GetMapping("/order/{orderId}/manage/{taskQueueId}/assign")
    public Mono<String> manageOrder(@PathVariable Integer orderId, @PathVariable Integer taskQueueId, Model model){
        model.addAttribute("employees", employeeService.findAllByTaskQueueId(taskQueueId) );
        model.addAttribute("taskQueue", managerService.findTaskQueueById(taskQueueId));
        model.addAttribute("order", managerService.findOrderById(orderId));
        return Mono.just("assign-task");
    }

    @PostMapping("/order/{orderId}/manage/{taskQueueId}/assign")
    public Mono<String> assignTask(@PathVariable Integer orderId, @PathVariable Integer taskQueueId, @ModelAttribute("taskQueue") TaskQueue taskQueue){
        return managerService.updateTaskQueue(taskQueue).thenReturn("orders");
    }

    @GetMapping("/order/{orderId}/manage/{taskQueueId}/complete")
    public Mono<String> setCompleteStatusForTask(@PathVariable Integer orderId, @PathVariable Integer taskQueueId, @ModelAttribute("taskQueue") TaskQueue taskQueue){
        return managerService
                .updateTaskStatusByTaskQueueId(taskQueueId, Status.COMPLETED)
                .thenReturn("redirect://manager/order/{orderId}/manage");
    }

}

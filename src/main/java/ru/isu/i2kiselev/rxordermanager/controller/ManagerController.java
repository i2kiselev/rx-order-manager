package ru.isu.i2kiselev.rxordermanager.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.isu.i2kiselev.rxordermanager.model.*;
import ru.isu.i2kiselev.rxordermanager.service.EmployeeService;
import ru.isu.i2kiselev.rxordermanager.service.ManagerService;
import ru.isu.i2kiselev.rxordermanager.service.TaskService;

/**
 * ManagerController. Core of the system
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
                .thenReturn("redirect:/manager/");
    }

    @PostMapping("/order/{orderId}/delete")
    public Mono<String> removeOrder(@PathVariable Integer orderId){
        return managerService.deleteAllTaskQueuesByOrderId(orderId)
                .then(managerService.deleteOrderById(orderId))
                .thenReturn("redirect:/manager/order/{orderId}/manage");
    }

    @GetMapping("/order/{orderId}/manage")
    public Mono<String> manageOrder(@PathVariable Integer orderId, Model model){
        model.addAttribute("completionTime", managerService.getAverageOrderCompletionTimeByOrderId(orderId));
        model.addAttribute("order", managerService.findOrderById(orderId));
        model.addAttribute("taskRecords", managerService.findAllTaskQueuesByOrderId(orderId));
        model.addAttribute("tasks", taskService.findAllByOrderId(orderId));
        model.addAttribute("assignedEmployees", employeeService.findAllAssignedToOrder(orderId));
        model.addAttribute("completed", Status.COMPLETED);
        model.addAttribute("isOrderCompleted", managerService.isOrderCompletedByOrderId(orderId));
        return Mono.just("order");
    }

    @GetMapping("/order/{orderId}/manage/{taskQueueId}/assign")
    public Mono<String> manageOrder(@PathVariable Integer orderId, @PathVariable Integer taskQueueId, Model model){
        model.addAttribute("employees", employeeService.findAllByTaskQueueId(taskQueueId) );
        model.addAttribute("taskQueue", managerService.findTaskQueueById(taskQueueId));
        model.addAttribute("order", managerService.findOrderById(orderId));
        return Mono.just("assign-task");
    }

    @PostMapping("/order/{orderId}/manage/{taskQueueId}/assign")
    public Mono<String> assignTaskToEmployee(@PathVariable Integer orderId, @PathVariable Integer taskQueueId, @ModelAttribute("taskQueue") TaskQueue taskQueue){
        return managerService.updateTaskQueue(taskQueue).thenReturn("redirect:/manager/order/{orderId}/manage");
    }

    @PostMapping("/order/{orderId}/manage/{taskQueueId}/delete")
    public Mono<String> deleteTaskFromOrder(@PathVariable Integer orderId, @PathVariable Integer taskQueueId, @ModelAttribute("taskQueue") TaskQueue taskQueue){
        return managerService.deleteTaskFromOrder(taskQueueId).thenReturn("redirect:/manager/order/{orderId}/manage");
    }

    @GetMapping("/report/daily")
    public Mono<String> getDailyReport(Model model){
        model.addAttribute("reportInfo", managerService.getDailyReportInfo());
        model.addAttribute("orderData", managerService.getDailyOrderReportData());
        return Mono.just("daily-report");
    }

    @GetMapping("/report/monthly")
    public Mono<String> getMonthlyReport(Model model){
        model.addAttribute("reportInfo", managerService.getMonthlyReportInfo());
        model.addAttribute("orderData", managerService.getMonthlyOrderReportData());
        return Mono.just("monthly-report");
    }
    @GetMapping("/report/order/{orderId}")
    public Mono<String> getOrderReport(@PathVariable Integer orderId, Model model){
        model.addAttribute("orderReport", managerService.getOrderReportById(orderId));
        model.addAttribute("report", managerService.getReportDataByOrderId(orderId));
        return Mono.just("order-report");
    }

    @GetMapping("/gantt/")
    @ResponseBody
    public Mono<GanttData> getDiagramData(){
        return managerService.getGanttDataForActiveOrders();
    }

}

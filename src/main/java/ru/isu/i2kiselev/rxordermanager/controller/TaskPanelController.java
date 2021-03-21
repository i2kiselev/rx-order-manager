package ru.isu.i2kiselev.rxordermanager.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.isu.i2kiselev.rxordermanager.model.Employee;
import ru.isu.i2kiselev.rxordermanager.model.Feedback;
import ru.isu.i2kiselev.rxordermanager.model.Status;
import ru.isu.i2kiselev.rxordermanager.model.TaskQueue;
import ru.isu.i2kiselev.rxordermanager.service.EmployeeService;
import ru.isu.i2kiselev.rxordermanager.service.ManagerService;
import ru.isu.i2kiselev.rxordermanager.service.TaskService;

import java.time.LocalDateTime;

/**
 * TaskPanelController. Allows employees to set statuses for assigned tasks and leave feedback after task completion
 * @author Ilya Kiselev
 */

@Controller
@Log4j2
@RequestMapping("/task-panel")
public class TaskPanelController {

    private final TaskService taskService;

    private final ManagerService managerService;

    private final EmployeeService employeeService;

    public TaskPanelController(TaskService taskService, ManagerService managerService, EmployeeService employeeService) {
        this.taskService = taskService;
        this.managerService = managerService;
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public Mono<String> getTasksOfCurrentEmployee(Model model){
        Mono<Integer> employeeId = employeeService.getCurrentEmployee().map(Employee::getId);
        model.addAttribute("tasks", employeeId.map(taskService::findAllAssignedTasksByEmployeeId).flatMapMany(Flux::from));
        model.addAttribute("taskRecords", employeeId.map(managerService::findAllTaskQueuesByEmployeeId).flatMapMany(Flux::from));
        model.addAttribute("completed", Status.COMPLETED);
        model.addAttribute("completing", Status.COMPLETING);
        return Mono.just("current-user-tasks");
    }

    @GetMapping("/{taskQueueId}/completing")
    public Mono<String> setCompletingStatusForTask(@PathVariable Integer taskQueueId, @ModelAttribute("taskQueue") TaskQueue taskQueue){
        return managerService
                .updateTaskStatusByTaskQueueId(taskQueueId, Status.COMPLETING)
                .then(managerService.setTaskQueueStartTime(taskQueueId, LocalDateTime.now()))
                .thenReturn("redirect:/task-panel/");
    }

    @GetMapping("/{taskQueueId}/complete")
    public Mono<String> getFeedbackForm(@PathVariable Integer taskQueueId, Model model){
        model.addAttribute("feedback", new Feedback());
        model.addAttribute("taskQueue", managerService.findTaskQueueById(taskQueueId));
        return Mono.just("feedback-form").doOnNext(x->log.info("Returned feedback-form view"));
    }

    @PostMapping("/{taskQueueId}/complete")
    public Mono<String> saveFeedbackAndSaveCompletedStatus(@PathVariable Integer taskQueueId, @ModelAttribute("feedback") Feedback feedback){
        return managerService.updateFeedbackByTaskQueueId(taskQueueId,feedback.getFeedback())
                .then(managerService.setTaskQueueCompletionTime(taskQueueId, LocalDateTime.now()))
                .thenReturn("redirect:/task-panel/");
    }

}

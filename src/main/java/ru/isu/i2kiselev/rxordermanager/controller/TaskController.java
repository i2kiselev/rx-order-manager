package ru.isu.i2kiselev.rxordermanager.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.isu.i2kiselev.rxordermanager.model.Task;
import ru.isu.i2kiselev.rxordermanager.service.TaskService;

@Controller
@RequestMapping("/task")
@Log4j2
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public Mono<String> index(Model model){
        model.addAttribute("tasks", taskService.findAll());
        log.info("Returned tasks view");
        return Mono.just("tasks");
    }

    @GetMapping("")
    public Mono<String> redirect(Model model){
        return index(model);
    }

    @GetMapping("/add")
    public Mono<String> addTask(Model model){
        log.info("Returned add-task view");
        model.addAttribute("task", new Task());
        return Mono.just("add-task");
    }

    @PostMapping("/add")
    public Mono<String> addTask(@ModelAttribute("task") Task task, Model model){
        return taskService.save(task).then(index(model));
    }

    @GetMapping("/{taskId}")
    public Mono<String> editEmployee(@PathVariable Integer taskId, Model model){
        log.info("Returned edit-task view");
        model.addAttribute("task", taskService.findById(taskId));
        return Mono.just("edit-task");
    }

    @PostMapping("/{taskId}/update")
    public Mono<String> updateEmployee(@PathVariable Integer taskId, @ModelAttribute Task task, Model model){
        return taskService.save(task).then(index(model));
    }

    @PostMapping("/{taskId}/delete")
    public Mono<String> removeEmployee(@PathVariable Integer taskId, Model model){
        return taskService.deleteById(taskId).then(index(model));
    }

}

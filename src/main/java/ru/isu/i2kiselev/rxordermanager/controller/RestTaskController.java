package ru.isu.i2kiselev.rxordermanager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.isu.i2kiselev.rxordermanager.model.Task;
import ru.isu.i2kiselev.rxordermanager.service.TaskService;

@RestController
@RequestMapping("/task")
public class RestTaskController {

    private final TaskService taskService;

    public RestTaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/save")
    public Mono<Task> save(Task task){
        return taskService.save(task);
    }

    @GetMapping("/findAll")
    public Flux<Task> findAll(){
        return taskService.findAll();
    }

    @GetMapping("/test")
    public Mono<Task> saveTaskTest(){
        Task task = new Task();
        task.setTaskName("Create ad for product");
        return taskService.save(task);
    }
}

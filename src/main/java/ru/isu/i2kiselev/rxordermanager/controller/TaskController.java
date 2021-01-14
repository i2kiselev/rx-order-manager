package ru.isu.i2kiselev.rxordermanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.isu.i2kiselev.rxordermanager.model.Task;
import ru.isu.i2kiselev.rxordermanager.service.TaskService;

@Controller
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/save")
    public Mono<Task> save(Task task){
        return taskService.saveTask(task);
    }

    @GetMapping("/findAll")
    public Flux<Task> findAll(){
        return taskService.findAll();
    }

}

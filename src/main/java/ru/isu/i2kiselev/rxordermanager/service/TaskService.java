package ru.isu.i2kiselev.rxordermanager.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.isu.i2kiselev.rxordermanager.model.Task;
import ru.isu.i2kiselev.rxordermanager.repository.TaskRepository;

/**
 * Singleton-service for CRUD-operations with Task class
 * @version 0.1
 * @author Ilya Kiselev
 */

@Service
public class TaskService {

    private final TaskRepository taskRepository;


    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Mono<Task> save(Task task){
        return taskRepository.save(task);
    }

    public Flux<Task> findAll(){
        return taskRepository.findAll();
    }


}

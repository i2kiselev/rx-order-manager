package ru.isu.i2kiselev.rxordermanager.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.isu.i2kiselev.rxordermanager.model.Task;
import ru.isu.i2kiselev.rxordermanager.repository.TaskRepository;

/**
 * Singleton-service for operations with Task class
 * @version 0.1
 * @author Ilya Kiselev
 */

@Service
@Log4j2
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Mono<Task> save(Task task){
        log.info("Saved task with id {}", task::getId);
        return taskRepository.save(task);
    }

    public Flux<Task> findAll(){
        log.info("Returned all tasks");
        return taskRepository.findAll();
    }

    public Flux<Task> findAllByEmployeeId(Integer employeeId){
        log.info("Returned all tasks for employee #{}", employeeId);
        return taskRepository.findAllByEmployeeId(employeeId);
    }

    public Flux<Task> findAllNotAddedByEmployeeId(Integer employeeId){
        log.info("Returned all tasks not added for employee #{}", employeeId);
        return taskRepository.findAllNotAddedByEmployeeId(employeeId);
    }

    public Mono<Task> findById(Integer taskId) {
        log.info("Returned task  with id {}", taskId);
        return taskRepository.findById(taskId);
    }

    public Mono<Void> deleteById(Integer taskId){
        log.info("Deleted task with id {}", taskId);
        return taskRepository.deleteById(taskId);
    }

    public Flux<Task> findAllByOrderId(Integer orderId){
        return taskRepository.findAllByOrderId(orderId);
    }
}

package ru.isu.i2kiselev.rxordermanager.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.isu.i2kiselev.rxordermanager.model.Task;
import ru.isu.i2kiselev.rxordermanager.repository.TaskRepository;

/**
 * Singleton-service for operations with Task class
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
        log.debug("Saved task with id {}", task::getId);
        return taskRepository.save(task);
    }

    public Flux<Task> findAll(){
        log.debug("Returned all tasks");
        return taskRepository.findAll();
    }

    public Flux<Task> findAllByEmployeeId(Integer employeeId){
        log.debug("Returned all tasks for employee #{}", employeeId);
        return taskRepository.findAllByEmployeeId(employeeId);
    }

    public Flux<Integer> findAllTaskEstimatesByEmployeeId(Integer employeeId){
        log.debug("Returned all estimates for employee #{}", employeeId);
        return taskRepository.findAllTaskEstimatesByEmployeeId(employeeId);
    }
    public Flux<Task> findAllAssignedTasksByEmployeeId(Integer employeeId){
        log.debug("Returned all tasks assigned for employee #{}", employeeId);
        return taskRepository.findAllAssignedTasksByEmployeeId(employeeId);
    }

    public Flux<Task> findAllNotAddedByEmployeeId(Integer employeeId){
        log.debug("Returned all tasks not added for employee #{}", employeeId);
        return taskRepository.findAllNotAddedByEmployeeId(employeeId);
    }

    public Mono<Task> findById(Integer taskId) {
        log.debug("Returned task  with id {}", taskId);
        return taskRepository.findById(taskId);
    }

    public Mono<Void> deleteById(Integer taskId){
        log.debug("Deleted task with id {}", taskId);
        return taskRepository.deleteById(taskId);
    }

    public Flux<Task> findAllByOrderId(Integer orderId){
        log.debug("Returned all tasks by order id {}", orderId);
        return taskRepository.findAllByOrderId(orderId);
    }
}

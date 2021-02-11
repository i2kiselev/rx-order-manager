package ru.isu.i2kiselev.rxordermanager.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.isu.i2kiselev.rxordermanager.model.Order;
import ru.isu.i2kiselev.rxordermanager.model.Status;
import ru.isu.i2kiselev.rxordermanager.model.TaskQueue;
import ru.isu.i2kiselev.rxordermanager.repository.OrderRepository;
import ru.isu.i2kiselev.rxordermanager.repository.TaskQueueRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for task distribution and order managing
 * @version 0.5
 * @author Ilya Kiselev
 */

@Service
@Log4j2
public class ManagerService {

    private final OrderRepository orderRepository;

    private final TaskQueueRepository taskQueueRepository;

    public ManagerService(OrderRepository orderRepository, TaskQueueRepository taskQueueRepository) {
        this.orderRepository = orderRepository;
        this.taskQueueRepository = taskQueueRepository;
    }

    public Mono<TaskQueue> updateTaskQueue(TaskQueue taskQueue){
        taskQueue.setAssignmentDate(LocalDateTime.now());
        taskQueue.setStatus(Status.ASSIGNED);
        return taskQueueRepository.save(taskQueue);
    }

    public Mono<TaskQueue> findTaskQueueById(Integer taskQueueId){
        return taskQueueRepository.findById(taskQueueId);
    }

    public Flux<TaskQueue> findAllTaskQueuesByOrderId(Integer orderId){
        return taskQueueRepository.findAllByOrderId(orderId);
    }

    public Mono<Integer> deleteAllTaskQueuesByOrderId(Integer orderId){
        return taskQueueRepository.deleteAllByOrderId(orderId)
                .doOnNext(x->log.info("Removed all task queues by order id {}", orderId));
    }

    public Mono<Order> findOrderById(Integer orderId){
        return orderRepository.findById(orderId);
    }

    public Flux<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    public Mono<Void> deleteOrderById(Integer orderId){
        return orderRepository.deleteById(orderId);
    }

    public Mono<Order> saveOrderFromForm(Order order){
        return  addCreationDateToOrder(order)
                .then(orderRepository.save(order))
                .then(addTaskIdsList(order))
                .then(addAllTasksToOrder(order))
                .doOnNext( x->
                    log.info("Saved order from form with id {}", x::getId)
                );
    }

    public Mono<TaskQueue> addTaskToOrderByOrderId(Integer orderId, Integer taskId){
        TaskQueue taskQueue = new TaskQueue(taskId, orderId, Status.ACCEPTED, LocalDateTime.now());
        return taskQueueRepository
                .save(taskQueue)
                .doOnNext(x->log.info("Added task #{} to order with id {}", taskId, orderId));
    }

    public Mono<Integer> updateTaskStatusByTaskQueueId(Integer taskQueueId, Status status){
        return taskQueueRepository.setTaskStatusByTaskQueueId(status,taskQueueId);
    }

    private Mono<Order> addAllTasksToOrder(Order order){
        return Flux.fromIterable(order.getTasks())
                .flatMap(x->addTaskToOrderByOrderId(order.getId(),x))
                .then(Mono.just(order));
    }

    private Mono<Order> addCreationDateToOrder(Order order){
        return Mono.just(order)
                .doOnNext(x->x.setCreationDate(LocalDateTime.now()));
    }

    private Mono<Order> addTaskIdsList(Order order){
        List<Integer> tasksIds = new ArrayList<>();
        for (int i = 0; i < order.getIds().size(); i++) {
            for (int j = 0; j < order.getQuantities().get(i); j++) {
                tasksIds.add(order.getIds().get(i));
            }
        }
        order.setTasks(tasksIds);
        return Mono.just(order);
    }

}

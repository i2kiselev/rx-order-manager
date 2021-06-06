package ru.isu.i2kiselev.rxordermanager.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.isu.i2kiselev.rxordermanager.model.*;
import ru.isu.i2kiselev.rxordermanager.repository.GanttDataRepository;
import ru.isu.i2kiselev.rxordermanager.repository.OrderRepository;
import ru.isu.i2kiselev.rxordermanager.repository.TaskQueueRepository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

/**
 * Service for task distribution and order managing
 * @author Ilya Kiselev
 */

@Service
@Log4j2
public class ManagerService {

    private final OrderRepository orderRepository;

    private final TaskQueueRepository taskQueueRepository;

    private final GanttDataRepository ganttDataRepository;

    public ManagerService(OrderRepository orderRepository, TaskQueueRepository taskQueueRepository, GanttDataRepository ganttDataRepository) {
        this.orderRepository = orderRepository;
        this.taskQueueRepository = taskQueueRepository;
        this.ganttDataRepository = ganttDataRepository;
    }

    public Mono<TaskQueue> updateTaskQueue(TaskQueue taskQueue){
        return prepareTaskQueueForAssignment(taskQueue).then(taskQueueRepository.save(taskQueue))
                .doOnNext(x->log.debug("Saved taskQueue with id {}", x.getId()));
    }

    public Mono<TaskQueue> findTaskQueueById(Integer taskQueueId){
        return taskQueueRepository.findById(taskQueueId)
                .doOnNext(x->log.debug("Returned taskQueue with id {}",taskQueueId));
    }

    public Flux<TaskQueue> findAllTaskQueuesByOrderId(Integer orderId){
        return taskQueueRepository.findAllByOrderId(orderId)
                .doOnNext(x->log.debug("Returned all taskQueues with orderId {}", orderId));
    }

    public Flux<TaskQueue> findAllTaskQueuesByEmployeeId(Integer employeeId){
        log.debug("Returned all tasks queues of employee #{}", employeeId);
        return taskQueueRepository.findAllByEmployeeId(employeeId);
    }

    public Mono<Integer> deleteAllTaskQueuesByOrderId(Integer orderId){
        return taskQueueRepository.deleteAllByOrderId(orderId)
                .doOnNext(x->log.debug("Removed all task queues by order id {}", orderId));
    }

    public Mono<Order> findOrderById(Integer orderId){
        return orderRepository.findById(orderId)
                .doOnNext(x->log.debug("Returned order with id {}", orderId));
    }

    public Flux<Order> findAllOrders() {
        return orderRepository.findAll()
                .doOnNext(x->log.debug("Returned all orders"));
    }

    public Mono<Void> deleteOrderById(Integer orderId){
        return orderRepository.deleteById(orderId)
                .doOnNext(x->log.debug("Removed order with id {}", orderId));
    }

    public Mono<Order> saveOrderFromForm(Order order){
        return  addCreationDateToOrder(order)
                .then(
                    orderRepository.save(order)
                                .flatMap(x->addTaskIdsList(x))
                                .flatMap(x->addAllTasksToOrder(x))
                                .doOnNext( x-> log.debug("Saved order from form with id {}",x::getId))
                );
    }

    public Mono<TaskQueue> addTaskToOrderByOrderId(Integer orderId, Integer taskId){
        TaskQueue taskQueue = new TaskQueue(taskId, orderId, Status.ACCEPTED);
        return taskQueueRepository
                .save(taskQueue)
                .doOnNext(x->log.debug("Added task #{} to order with id {}", taskId, orderId));
    }

    public Mono<Integer> updateTaskStatusByTaskQueueId(Integer taskQueueId, Status status){
        return taskQueueRepository.setTaskStatusByTaskQueueId(status,taskQueueId)
                .doOnNext(x->log.debug("Set status of taskQueue with id {} to {}", taskQueueId, status));
    }

    public Mono<Integer> updateFeedbackByTaskQueueId(Integer taskQueueId, String feedback){
        return taskQueueRepository.updateFeedbackByTaskQueueId(feedback,taskQueueId)
                .doOnNext(x->log.debug("Set feedback of taskQueue with id {} to {}", taskQueueId, feedback.substring(0, Math.min(feedback.length(),15))));
    }

    public Mono<Integer> setTaskQueueCompletionTime(Integer taskQueueId, LocalDateTime localDateTime){
        return taskQueueRepository.setTaskQueueCompletionTime( localDateTime, taskQueueId)
                .doOnNext(x->log.debug("Set completion time of taskQueue with id {} ", taskQueueId));
    }

    public Mono<Integer> setTaskQueueStartTime(Integer taskQueueId, LocalDateTime localDateTime){
        return taskQueueRepository.setTaskQueueStartTime( localDateTime, taskQueueId)
                .doOnNext(x->log.debug("Set completion time of taskQueue with id {} ", taskQueueId));
    }

    public Mono<GanttData> getGanttDataForActiveOrders(){
       return getGanttInfoForActiveOrders().collectList().map(GanttData::new);
    }

    public Flux<GanttInfo> getGanttInfoForActiveOrders(){
        return ganttDataRepository.getChartDataForActive();
    }

    public Mono<String> getAverageOrderCompletionTimeByOrderId(Integer orderId){
        return taskQueueRepository.findAll()
                .filter(x->x.getOrderId().equals(orderId))
                .flatMap(x->getAverageTaskCompletionTimeByTaskId(x.getTaskId()))
                .reduce(Long::sum)
                .map(x->{
                    Date date = new Date(x);
                    DateFormat formatter = new SimpleDateFormat("HH:mm");
                    formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
                    return formatter.format(date);
                })
                .doOnNext(x->log.debug("Returned average completion time {} of order {}", x, orderId));
    }

    private Mono<Long> getAverageTaskCompletionTimeByTaskId(Integer taskId){
        return taskQueueRepository.findAll()
                .filter(x->x.getTaskId().equals(taskId))
                .filter(x->x.getStartDate()!=null&&x.getCompletionDate()!=null)
                .collect(Collectors.averagingLong(x-> ChronoUnit.MILLIS.between(x.getStartDate(),x.getCompletionDate())))
                .map(Double::longValue)
                .switchIfEmpty(Mono.just(0L));
    }

    /*private Flux<GanttData> getGanttDataForActiveOrders(){
        return taskQueueRepository.findAll()
                .filter(x->x.getStatus()!=Status.COMPLETED)
                .
    }
    */


    private Flux<TaskQueue> getUnassignedActiveTasks(){
        return taskQueueRepository.findAll().filter(x->x.getEmployeeId()==null).filter(x->!x.getStatus().equals(Status.COMPLETED));
    }

    private Flux<TaskQueue> getAssignedActiveTasks(){
        return taskQueueRepository.findAll().filter(x->x.getEmployeeId()!=null).filter(x->!x.getStatus().equals(Status.COMPLETED));
    }

    public Mono<Boolean> isOrderCompletedByOrderId(Integer orderId){
        return taskQueueRepository.isOrderFinished(orderId);
    }

    private Mono<Void> prepareTaskQueueForAssignment(TaskQueue taskQueue){
        taskQueue.setAssignmentDate(LocalDateTime.now());
        taskQueue.setStatus(Status.ASSIGNED);
        return Mono.empty();
    }

    private Mono<Order> addAllTasksToOrder(Order order){
        return Flux.fromIterable(order.getTasks())
                .flatMap(x->addTaskToOrderByOrderId(order.getId(),x))
                .then(Mono.just(order));
    }

    private Mono<Order> addCreationDateToOrder(Order order){
        order.setCreationDate(LocalDateTime.now());
        return Mono.just(order);
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

package ru.isu.i2kiselev.rxordermanager.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.isu.i2kiselev.rxordermanager.model.Order;
import ru.isu.i2kiselev.rxordermanager.model.Task;
import ru.isu.i2kiselev.rxordermanager.repository.OrderRepository;

@Service
@Log4j2
public class OrderService {

    private OrderRepository orderRepository;

    public Mono<Order> save(Order order) {
        return orderRepository.save(order);
    }

    public Mono<Integer> addTaskToOrderByOrderId(Integer orderId, Integer taskId){
        return orderRepository.addTaskToOrderByOrderId(orderId,taskId);
    }

    public Mono<Integer> addTaskToOrderByOrderId(Order order, Task task){
        return orderRepository.addTaskToOrderByOrderId(order.getId(),task.getId());
    }
}

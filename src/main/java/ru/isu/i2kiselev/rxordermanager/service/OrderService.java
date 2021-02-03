package ru.isu.i2kiselev.rxordermanager.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.isu.i2kiselev.rxordermanager.model.Order;
import ru.isu.i2kiselev.rxordermanager.model.Task;
import ru.isu.i2kiselev.rxordermanager.repository.OrderRepository;

/**
 * Service for task distribution and order managing
 * @version 0.1
 * @author Ilya Kiselev
 */

@Service
@Log4j2
public class OrderService {

    private OrderRepository orderRepository;

    public Mono<Order> save(Order order) {
        return orderRepository.save(order);
    }

    public Mono<Order> saveFromForm(Order order){
        return orderRepository.save(order).doOnNext(x->{
            for (int i =0; i<order.getIds().size();i++ ) {
                for (int j = 0; j < order.getQuantities().get(i); j++) {
                    addTaskToOrderByOrderId(x.getId(),order.getIds().get(i));
                }
            }
        });
    }

    public Mono<Integer> addTaskToOrderByOrderId(Integer orderId, Integer taskId){
        return orderRepository.addTaskToOrderByOrderId(orderId,taskId);
    }

    public Mono<Integer> addTaskToOrderByOrderId(Order order, Task task){
        return orderRepository.addTaskToOrderByOrderId(order.getId(),task.getId());
    }

    public Mono<Integer> addTaskToOrderByOrderIdMultipleTimes(Integer orderId, Integer taskId, Integer quantity){
        for (int j = 0; j < quantity; j++) {
            addTaskToOrderByOrderId(orderId,taskId);
        }
        return orderRepository.addTaskToOrderByOrderId(orderId,taskId);
    }

}

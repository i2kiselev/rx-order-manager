package ru.isu.i2kiselev.rxordermanager.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import ru.isu.i2kiselev.rxordermanager.model.Order;

@Repository
public interface OrderRepository extends ReactiveCrudRepository<Order,Integer> {

    @Query("insert into order_table values ($1,$2)")
    Mono<Void> addTaskToOrderByOrderId(Integer orderId, Integer taskId);


}

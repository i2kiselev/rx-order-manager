package ru.isu.i2kiselev.rxordermanager.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import ru.isu.i2kiselev.rxordermanager.model.Order;
import ru.isu.i2kiselev.rxordermanager.model.Status;

import java.time.LocalDateTime;

/**
 * Order repository.
 * @version 0.3
 * @author Ilya Kiselev
 */

@Repository
public interface OrderRepository extends ReactiveCrudRepository<Order,Integer> {

    @Query("insert into task_queue (order_id,task_id,status,assignment_date) values ($1,$2, $3, $4)")
    Mono<Void> addTaskToOrderByOrderId(Integer orderId, Integer taskId, Status status,  LocalDateTime assignmentDate);

}

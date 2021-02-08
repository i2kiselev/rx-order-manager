package ru.isu.i2kiselev.rxordermanager.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.isu.i2kiselev.rxordermanager.model.TaskQueue;

/**
 * TaskQueue repository.
 * @version 0.2
 * @author Ilya Kiselev
 */

@Repository
public interface TaskQueueRepository extends ReactiveCrudRepository<TaskQueue,Integer> {

    @Query("select * from task_queue where order_id=$1")
    Flux<TaskQueue> findAllByOrderId(Integer orderId);

    @Query("DELETE FROM task_queue WHERE task_queue.order_id = $1")
    Mono<Integer> deleteAllByOrderId(Integer orderId);
}

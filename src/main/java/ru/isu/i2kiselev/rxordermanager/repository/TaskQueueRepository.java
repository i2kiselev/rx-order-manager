package ru.isu.i2kiselev.rxordermanager.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.isu.i2kiselev.rxordermanager.model.Status;
import ru.isu.i2kiselev.rxordermanager.model.TaskQueue;

import java.time.LocalDateTime;

/**
 * TaskQueue repository.
 * @author Ilya Kiselev
 */

@Repository
public interface TaskQueueRepository extends ReactiveCrudRepository<TaskQueue,Integer> {

    @Query("select * from task_queue where order_id=$1 order by task_queue.id")
    Flux<TaskQueue> findAllByOrderId(Integer orderId);

    @Query("DELETE FROM task_queue WHERE task_queue.order_id = $1")
    Mono<Integer> deleteAllByOrderId(Integer orderId);

    @Query("update task_queue set status=$1 where id = $2 ")
    Mono<Integer> setTaskStatusByTaskQueueId(Status status, Integer taskQueueId);

    @Query("select count(*)=0 as is_completed from task_queue where order_id=$1 and status !='COMPLETED'")
    Mono<Boolean> isOrderFinished(Integer orderId);

    @Query("update task_queue set completion_date=$1 where id = $2 ")
    Mono<Integer> setTaskQueueCompletionTime(LocalDateTime localDateTime, Integer taskQueueId);

    @Query("update task_queue set start_date=$1 where id = $2 ")
    Mono<Integer> setTaskQueueStartTime(LocalDateTime localDateTime, Integer taskQueueId);

    @Query("SELECT * FROM public.task_queue where task_queue.employee_id=$1" +
            "ORDER BY id ASC ")
    Flux<TaskQueue> findAllByEmployeeId(Integer employeeId);

    @Query("update task_queue set feedback=$1 where id = $2")
    Mono<Integer> updateFeedbackByTaskQueueId( String feedback,Integer taskQueueId);

}

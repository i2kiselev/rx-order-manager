package ru.isu.i2kiselev.rxordermanager.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import ru.isu.i2kiselev.rxordermanager.model.Task;

/**
 * Task repository.
 * @author Ilya Kiselev
 */

@Repository
public interface TaskRepository extends ReactiveCrudRepository<Task,Integer> {

    @Query("select task.id,task.task_name from task " +
            "left join employee_task_estimates " +
            "on task.id=employee_task_estimates.task_id " +
            "where employee_id=$1")
    Flux<Task> findAllByEmployeeId(Integer employeeId);

    @Query("select * from task " +
            "except " +
            "(select task.id,task.task_name from task " +
            "left join employee_task_estimates " +
            "on task.id=employee_task_estimates.task_id " +
            "where employee_id=$1)")
    Flux<Task> findAllNotAddedByEmployeeId(Integer employeeId);

    @Query("select task.id,task.task_name from task " +
            "left join task_queue " +
            "on task.id=task_queue.task_id  " +
            "where order_id=$1 " +
            "order by task_queue.id")
    Flux<Task> findAllByOrderId(Integer orderId);

    @Query("select task.id,task.task_name from task " +
            "left join task_queue " +
            "on task.id=task_queue.task_id " +
            "where employee_id= $1 ORDER BY task_queue.id ASC")
    Flux<Task> findAllAssignedTasksByEmployeeId(Integer employeeId);

}

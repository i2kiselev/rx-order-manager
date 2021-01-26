package ru.isu.i2kiselev.rxordermanager.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import ru.isu.i2kiselev.rxordermanager.model.Task;

/**
 * Task repository.
 * @version 0.2
 * @author Ilya Kiselev
 */

@Repository
public interface TaskRepository extends ReactiveCrudRepository<Task,Integer> {

    @Query("select * from public.task " +
            "join employee_task_estimates id " +
            "on task.id=employee_task_estimates.task_id " +
            "where employee_task_estimates.employee_id=$1")
    Flux<Task> findAllByEmployeeId(Integer employeeId);

}

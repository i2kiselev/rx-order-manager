package ru.isu.i2kiselev.rxordermanager.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import ru.isu.i2kiselev.rxordermanager.model.GanttData;

public interface GanttDataRepository extends ReactiveCrudRepository<GanttData,Integer> {
    @Query("select task_queue.id, task.task_name, task_queue.start_date, employee_task_estimates.completion_time as duration " +
            "from task_queue " +
            "left join task on task_queue.task_id=task.id " +
            "left join employee_task_estimates on task_queue.employee_id=employee_task_estimates.employee_id and task_queue.task_id=employee_task_estimates.task_id where task_queue.order_id=$1")
    Flux<GanttData> getChartDataByOrderId(Integer orderId);
}

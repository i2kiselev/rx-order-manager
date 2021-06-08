package ru.isu.i2kiselev.rxordermanager.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import ru.isu.i2kiselev.rxordermanager.model.GanttElement;
import ru.isu.i2kiselev.rxordermanager.model.GanttInfo;

public interface GanttDataRepository extends ReactiveCrudRepository<GanttInfo,Integer> {

    @Query("select task_queue.id, task.task_name as text, task_queue.assignment_date as task_assignment_date, order_table.name as order_name, order_table.creation_date as order_creation_date, employee_task_estimates.completion_time as employee_duration, task.default_estimate as default_duration, order_table.id as order_id, task_queue.completion_date as task_finish_date " +
            "from task_queue " +
            "left join task on task_queue.task_id=task.id " +
            "left join employee_task_estimates on task_queue.employee_id=employee_task_estimates.employee_id and task_queue.task_id=employee_task_estimates.task_id " +
            "left join order_table on task_queue.order_id=order_table.id "+
            "where task_queue.status!='COMPLETED'")
    Flux<GanttInfo> getChartDataForActive();
}

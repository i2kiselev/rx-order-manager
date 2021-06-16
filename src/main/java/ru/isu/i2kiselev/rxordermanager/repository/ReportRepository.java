package ru.isu.i2kiselev.rxordermanager.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import ru.isu.i2kiselev.rxordermanager.model.Report;

@Repository
public interface ReportRepository extends ReactiveCrudRepository<Report, Integer> {

    @Query("select task.task_name, " +
            "employee.username as employee_name, " +
            "task_queue.feedback, " +
            "employee_task_estimates.completion_time as estimate_duration, " +
            "ROUND(CAST(EXTRACT(EPOCH FROM task_queue.completion_date-task_queue.start_date)/3600 as numeric),1) as duration " +
            "from task_queue " +
            "left join task on task_queue.task_id=task.id " +
            "left join order_table on task_queue.order_id=order_table.id " +
            "left join employee_task_estimates on task_queue.employee_id=employee_task_estimates.employee_id and task_queue.task_id=employee_task_estimates.task_id " +
            "left join employee on task_queue.employee_id=employee.id " +
            "where employee_task_estimates.completion_time<ROUND(CAST(EXTRACT(EPOCH FROM task_queue.completion_date-task_queue.start_date)/3600 as numeric),1) and order_table.id = $1")
    Flux<Report> getReportByOrderId(Integer orderId);

}

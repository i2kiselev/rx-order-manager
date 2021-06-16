package ru.isu.i2kiselev.rxordermanager.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import ru.isu.i2kiselev.rxordermanager.model.ReportInfo;

@Repository
public interface ReportInfoRepository extends ReactiveCrudRepository<ReportInfo, Integer> {

    @Query("select current_date as date_of_report, " +
            "(select count(task_queue.id) from task_queue left join order_table on order_table.id=task_queue.order_id left join employee_task_estimates on task_queue.employee_id=employee_task_estimates.employee_id and task_queue.task_id=employee_task_estimates.task_id where order_table.creation_date >= current_date and order_table.creation_date < current_date + interval '1 day' and task_queue.completion_date IS NOT NULL AND EXTRACT(EPOCH FROM task_queue.completion_date-task_queue.start_date)/3600<=employee_task_estimates.completion_time) as tasks_finished_in_time, " +
            "(select count(task_queue.id) from task_queue left join order_table on order_table.id=task_queue.order_id left join employee_task_estimates on task_queue.employee_id=employee_task_estimates.employee_id and task_queue.task_id=employee_task_estimates.task_id where order_table.creation_date >= current_date and order_table.creation_date < current_date + interval '1 day' and task_queue.completion_date IS NOT NULL AND EXTRACT(EPOCH FROM task_queue.completion_date-task_queue.start_date)/3600>employee_task_estimates.completion_time) as tasks_not_finished_in_time, " +
            "(select count(task_queue.id) from task_queue left join order_table on order_table.id=task_queue.order_id left join employee_task_estimates on task_queue.employee_id=employee_task_estimates.employee_id and task_queue.task_id=employee_task_estimates.task_id where order_table.creation_date >= current_date and order_table.creation_date < current_date + interval '1 day' and task_queue.completion_date IS NULL) as tasks_in_progress")
    Mono<ReportInfo> getDailyReportInfo();

    @Query("select current_date as date_of_report, " +
            "(select count(task_queue.id) from task_queue left join order_table on order_table.id=task_queue.order_id left join employee_task_estimates on task_queue.employee_id=employee_task_estimates.employee_id and task_queue.task_id=employee_task_estimates.task_id where EXTRACT(YEAR FROM order_table.creation_date)=EXTRACT(YEAR FROM current_date) and EXTRACT(MONTH FROM order_table.creation_date)=EXTRACT(MONTH FROM current_date) and task_queue.completion_date IS NOT NULL AND EXTRACT(EPOCH FROM task_queue.completion_date-task_queue.start_date)/3600<=employee_task_estimates.completion_time) as tasks_finished_in_time, " +
            "(select count(task_queue.id) from task_queue left join order_table on order_table.id=task_queue.order_id left join employee_task_estimates on task_queue.employee_id=employee_task_estimates.employee_id and task_queue.task_id=employee_task_estimates.task_id where EXTRACT(YEAR FROM order_table.creation_date)=EXTRACT(YEAR FROM current_date) and EXTRACT(MONTH FROM order_table.creation_date)=EXTRACT(MONTH FROM current_date) and task_queue.completion_date IS NOT NULL AND EXTRACT(EPOCH FROM task_queue.completion_date-task_queue.start_date)/3600>employee_task_estimates.completion_time) as tasks_not_finished_in_time, " +
            "(select count(task_queue.id) from task_queue left join order_table on order_table.id=task_queue.order_id left join employee_task_estimates on task_queue.employee_id=employee_task_estimates.employee_id and task_queue.task_id=employee_task_estimates.task_id where EXTRACT(YEAR FROM order_table.creation_date)=EXTRACT(YEAR FROM current_date) and EXTRACT(MONTH FROM order_table.creation_date)=EXTRACT(MONTH FROM current_date) and task_queue.completion_date IS NULL) as tasks_in_progress")
    Mono<ReportInfo> getMonthlyReportInfo();
}

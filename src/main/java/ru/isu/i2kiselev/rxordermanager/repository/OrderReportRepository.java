package ru.isu.i2kiselev.rxordermanager.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.isu.i2kiselev.rxordermanager.model.OrderReport;

@Repository
public interface OrderReportRepository extends ReactiveCrudRepository<OrderReport,Integer> {

    @Query("select order_table.id as order_id, order_table.name as order_name, current_date as current_day, " +
            "(select count(task_queue.id) from task_queue left join employee_task_estimates on task_queue.employee_id=employee_task_estimates.employee_id and task_queue.task_id=employee_task_estimates.task_id and task_queue.order_id=$1 where task_queue.completion_date IS NOT NULL AND EXTRACT(EPOCH FROM task_queue.completion_date-task_queue.start_date)/3600<employee_task_estimates.completion_time) as tasks_finished_in_time, " +
            "(select count(task_queue.id) from task_queue left join employee_task_estimates on task_queue.employee_id=employee_task_estimates.employee_id and task_queue.task_id=employee_task_estimates.task_id and task_queue.order_id=$1 where task_queue.completion_date IS NOT NULL AND EXTRACT(EPOCH FROM task_queue.completion_date-task_queue.start_date)/3600>employee_task_estimates.completion_time) as tasks_not_finished_in_time, " +
            "(select count(task_queue.id) from task_queue left join employee_task_estimates on task_queue.employee_id=employee_task_estimates.employee_id and task_queue.task_id=employee_task_estimates.task_id and task_queue.order_id=$1 where task_queue.completion_date IS NULL) as tasks_in_progress " +
            "from order_table where order_table.id = $1")
    Mono<OrderReport> getOrderReportByOrderId(Integer orderId);

    @Query("select order_table.id from order_table where EXTRACT(YEAR FROM order_table.creation_date)=EXTRACT(YEAR FROM current_date) and EXTRACT(MONTH FROM order_table.creation_date)=EXTRACT(MONTH FROM current_date);")
    Flux<Integer> getMonthlyOrderIdList();

    @Query("select order_table.id from order_table where order_table.creation_date >= current_date and order_table.creation_date < current_date + interval '1 day';")
    Flux<Integer> getDailyOrderIdList();
}

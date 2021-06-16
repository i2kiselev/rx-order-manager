package ru.isu.i2kiselev.rxordermanager.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import ru.isu.i2kiselev.rxordermanager.model.EmployeeReport;

@Repository
public interface EmployeeReportRepository extends ReactiveCrudRepository<EmployeeReport, Integer> {

    @Query("select employee.username, " +
            "(select count(task_queue.id) from task_queue left join employee_task_estimates on task_queue.employee_id=employee_task_estimates.employee_id and task_queue.task_id=employee_task_estimates.task_id where task_queue.employee_id=$1 and task_queue.completion_date IS NOT NULL AND EXTRACT(EPOCH FROM task_queue.completion_date-task_queue.start_date)/3600<=employee_task_estimates.completion_time) as tasks_finished_in_time, " +
            "(select count(task_queue.id) from task_queue left join employee_task_estimates on task_queue.employee_id=employee_task_estimates.employee_id and task_queue.task_id=employee_task_estimates.task_id where task_queue.employee_id=$1 and task_queue.completion_date IS NOT NULL AND EXTRACT(EPOCH FROM task_queue.completion_date-task_queue.start_date)/3600>employee_task_estimates.completion_time) as tasks_not_finished_in_time, " +
            "(select count(task_queue.id) from task_queue left join employee_task_estimates on task_queue.employee_id=employee_task_estimates.employee_id and task_queue.task_id=employee_task_estimates.task_id where task_queue.employee_id=$1 and task_queue.completion_date IS NULL) as tasks_in_progress \n" +
            "from employee where employee.id=$1")
    Flux<EmployeeReport> getEmployeeReportByEmployeeId(Integer employeeId);

    @Query("select distinct employee.id " +
            "from employee " +
            "left join task_queue on " +
            "task_queue.employee_id=employee.id " +
            "where task_queue.completion_date is not null " +
            "and task_queue.completion_date>= current_date " +
            "and task_queue.completion_date < current_date + interval '1 day'")
    Flux<Integer> getDailyEmployeeIdList();

    @Query("select distinct employee.id " +
            "from employee " +
            "left join task_queue " +
            "on task_queue.employee_id=employee.id " +
            "where task_queue.completion_date is not null " +
            "and EXTRACT(YEAR FROM task_queue.completion_date)=EXTRACT(YEAR FROM current_date) " +
            "and EXTRACT(MONTH FROM task_queue.completion_date)=EXTRACT(MONTH FROM current_date)")
    Flux<Integer> getMonthlyEmployeeIdList();
}

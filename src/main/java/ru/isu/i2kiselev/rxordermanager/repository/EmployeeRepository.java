package ru.isu.i2kiselev.rxordermanager.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.isu.i2kiselev.rxordermanager.model.Employee;

/**
 * Employee repository.
 * @version 0.5
 * @author Ilya Kiselev
 */

@Repository
public interface EmployeeRepository extends ReactiveCrudRepository<Employee, Integer> {

    @Query("select employee.id, employee.username " +
            "from employee " +
            "left join employee_task_estimates on " +
            "employee.id=employee_task_estimates.employee_id " +
            "where task_id=$1")
    Flux<Employee> findAllByTaskId(Integer task_id);

    @Query("insert into employee_task_estimates values($1,$2,$3)")
    Mono<Integer> addTaskEstimateToEmployee(Integer employee_id, Integer task_id, Integer estimate);

    @Query("update employee_task_estimates set(estimate) = ($3) " +
            "where employee_task_estimates.employee_id=$1 and " +
            "      employee_task_estimates.task_id=$2")
    Mono<Integer> updateTaskEstimateOfEmployee(Integer employee_id, Integer task_id, Integer estimate);

    @Query("delete from employee_task_estimates " +
            "where employee_task_estimates.employee_id=$1 and " +
            "employee_task_estimates.task_id=$2")
    Mono<Integer> removeTaskEstimateToEmployee(Integer employee, Integer task_id);

}

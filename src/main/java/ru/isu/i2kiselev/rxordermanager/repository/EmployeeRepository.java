package ru.isu.i2kiselev.rxordermanager.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import ru.isu.i2kiselev.rxordermanager.model.Employee;

@Repository
public interface EmployeeRepository extends ReactiveCrudRepository<Employee, Integer> {
    @Query("SELECT ")
    Flux<Employee> findAllByTask(Integer taskId);
}

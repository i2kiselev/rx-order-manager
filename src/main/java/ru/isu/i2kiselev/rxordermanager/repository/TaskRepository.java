package ru.isu.i2kiselev.rxordermanager.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends ReactiveCrudRepository<Task,Integer> {
}

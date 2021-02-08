package ru.isu.i2kiselev.rxordermanager.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import ru.isu.i2kiselev.rxordermanager.model.TaskQueue;

/**
 * TaskQueue repository.
 * @version 0.1
 * @author Ilya Kiselev
 */

@Repository
public interface TaskQueueRepository extends ReactiveCrudRepository<TaskQueue,Integer> {

}

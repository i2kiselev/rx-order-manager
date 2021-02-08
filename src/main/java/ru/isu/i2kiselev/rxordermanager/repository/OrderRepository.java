package ru.isu.i2kiselev.rxordermanager.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import ru.isu.i2kiselev.rxordermanager.model.Order;

/**
 * Order repository.
 * @version 0.3
 * @author Ilya Kiselev
 */

@Repository
public interface OrderRepository extends ReactiveCrudRepository<Order,Integer> {

}

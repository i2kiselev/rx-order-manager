package ru.isu.i2kiselev.rxordermanager.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import ru.isu.i2kiselev.rxordermanager.model.Order;

@Repository
public interface OrderRepository extends ReactiveCrudRepository<Order,Integer> {

}

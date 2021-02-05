package ru.isu.i2kiselev.rxordermanager.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.isu.i2kiselev.rxordermanager.model.Order;
import ru.isu.i2kiselev.rxordermanager.model.Status;
import ru.isu.i2kiselev.rxordermanager.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void saveFromFormAddsCreationDateTest() {
        Order order = new Order();
        order.setId(1);
        List<Integer> taskIDs= new ArrayList<>();
        taskIDs.add(1);
        taskIDs.add(1);
        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        List<Integer> quantities = new ArrayList<>();
        quantities.add(2);
        order.setIds(ids);
        order.setQuantities(quantities);
        given(orderRepository.save(any(Order.class))).willReturn(Mono.just(order));
        given(orderRepository.addTaskToOrderByOrderId(any(Integer.class),any(Integer.class),any(Status.class),any(LocalDateTime.class))).willReturn(Mono.just("").then());
        Mono<Order> savedOrder = orderService.saveFromForm(order);
        StepVerifier.create(savedOrder)
                .thenConsumeWhile(result -> {
                    assertThat(result).isNotNull();
                    assertThat(result.getId()).isEqualTo(order.getId());
                    assertThat(result.getCreationDate()).isNotNull();
                    return true;
                })
                .verifyComplete();
    }

    @Test
    void saveFromFormAddsTasksIdsListTest() {
        Order order = new Order();
        order.setId(1);
        List<Integer> taskIDs= new ArrayList<>();
        taskIDs.add(1);
        taskIDs.add(1);
        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        List<Integer> quantities = new ArrayList<>();
        quantities.add(2);
        order.setIds(ids);
        order.setQuantities(quantities);
        given(orderRepository.save(any(Order.class))).willReturn(Mono.just(order));
        given(orderRepository.addTaskToOrderByOrderId(any(Integer.class),any(Integer.class),any(Status.class),any(LocalDateTime.class))).willReturn(Mono.just("").then());
        Mono<Order> savedOrder = orderService.saveFromForm(order);
        StepVerifier.create(savedOrder)
                .thenConsumeWhile(result -> {
                    assertThat(result).isNotNull();
                    assertThat(result.getId()).isEqualTo(order.getId());
                    assertThat(result.getTasks()).isEqualTo(taskIDs);
                    return true;
                })
                .verifyComplete();
    }

    @Test
    void findAllReturnsFluxTest() {
        List<Order> orders = new ArrayList<>();
        Order order1 = new Order();
        order1.setId(1);
        Order order2 = new Order();
        order2.setId(2);
        orders.add(order1);
        orders.add(order2);
        given(orderRepository.findAll()).willReturn(Flux.fromIterable(orders));
        Flux<Order> orderFlux = orderService.findAll();
        StepVerifier.create(orderFlux)
                .expectNext(order1,order2)
                .expectNextCount(0)
                .expectComplete()
                .verify();
    }

}
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
import ru.isu.i2kiselev.rxordermanager.model.TaskQueue;
import ru.isu.i2kiselev.rxordermanager.repository.OrderRepository;
import ru.isu.i2kiselev.rxordermanager.repository.TaskQueueRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class ManagerServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private TaskQueueRepository taskQueueRepository;

    @InjectMocks
    private ManagerService managerService;

    @Test
    void updateTaskForQueueAddsAssignTime(){
        TaskQueue taskQueue = new TaskQueue();
        taskQueue.setId(1);
        given(taskQueueRepository.save(any(TaskQueue.class))).willReturn(Mono.just(taskQueue));
        Mono<TaskQueue> taskQueueMono = managerService.updateTaskQueue(taskQueue);
        StepVerifier.create(taskQueueMono)
                .thenConsumeWhile(result -> {
                    assertThat(result).isNotNull();
                    assertThat(result.getAssignmentDate()).isNotNull();
                    assertThat(result.getId()).isEqualTo(taskQueue.getId());
                    return true;
                })
                .verifyComplete();
    }

    @Test
    void findTaskQueueByIdReturnsTaskQueueMonoTest(){
        TaskQueue taskQueue = new TaskQueue();
        taskQueue.setId(1);
        given(taskQueueRepository.findById(any(Integer.class))).willReturn(Mono.just(taskQueue));
        Mono<TaskQueue> taskQueueMono = managerService.findTaskQueueById(1);
        StepVerifier.create(taskQueueMono)
                .thenConsumeWhile(result -> {
                    assertThat(result).isNotNull();
                    assertThat(result.getId()).isEqualTo(taskQueue.getId());
                    return true;
                })
                .verifyComplete();
    }

    @Test
    void findAllTaskQueuesByOrderIdReturnsFluxTest() {
        List<TaskQueue> taskQueues = new ArrayList<>();
        TaskQueue taskQueue = new TaskQueue();
        taskQueue.setId(1);
        TaskQueue taskQueue2 = new TaskQueue();
        taskQueue2.setId(2);
        taskQueues.add(taskQueue);
        taskQueues.add(taskQueue2);
        given(taskQueueRepository.findAllByOrderId(any(Integer.class))).willReturn(Flux.fromIterable(taskQueues));
        Flux<TaskQueue> taskQueueFlux= managerService.findAllTaskQueuesByOrderId(1);
        StepVerifier.create(taskQueueFlux)
                .expectNext(taskQueue,taskQueue2)
                .expectNextCount(0)
                .expectComplete()
                .verify();
    }

    @Test
    void saveFromFormAddsCreationDateTest() {
        Order order = new Order();
        order.setId(1);
        List<Integer> taskIDs= new ArrayList<>();
        taskIDs.add(1);
        taskIDs.add(1);
        List<Integer> quantities = new ArrayList<>();
        quantities.add(2);
        quantities.add(2);
        order.setIds(taskIDs);
        order.setQuantities(quantities);
        given(orderRepository.save(any(Order.class))).willReturn(Mono.just(order));
        given(taskQueueRepository.save(any(TaskQueue.class))).willReturn(Mono.just(new TaskQueue()));
        Mono<Order> savedOrder = managerService.saveOrderFromForm(order);
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
        given(taskQueueRepository.save(any(TaskQueue.class))).willReturn(Mono.just(new TaskQueue()));
        Mono<Order> savedOrder = managerService.saveOrderFromForm(order);
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
    void findAllOrdersReturnsFluxTest() {
        List<Order> orders = new ArrayList<>();
        Order order1 = new Order();
        order1.setId(1);
        Order order2 = new Order();
        order2.setId(2);
        orders.add(order1);
        orders.add(order2);
        given(orderRepository.findAll()).willReturn(Flux.fromIterable(orders));
        Flux<Order> orderFlux = managerService.findAllOrders();
        StepVerifier.create(orderFlux)
                .expectNext(order1,order2)
                .expectNextCount(0)
                .expectComplete()
                .verify();
    }

    @Test
    void findOrderByIdReturnsOrderMonoTest(){
        Order order = new Order();
        order.setDescription("Test name");
        given(orderRepository.findById(any(Integer.class))).willReturn(Mono.just(order));
        Mono<Order> orderMono = managerService.findOrderById(1);
        StepVerifier.create(orderMono)
                .thenConsumeWhile(result -> {
                    assertThat(result).isNotNull();
                    assertThat(result.getDescription()).isEqualTo(order.getDescription());
                    return true;
                })
                .verifyComplete();
    }

    @Test
    void addTaskToOrderByOrderIdCreatesTaskQueueWithStatusAndCreationTime(){
        Integer taskId = 1;
        Integer orderId = 1;
        TaskQueue taskQueue = new TaskQueue(taskId, orderId, Status.ACCEPTED, LocalDateTime.now());
       given(taskQueueRepository.save(any(TaskQueue.class))).willReturn(Mono.just(taskQueue));
        Mono<TaskQueue> taskQueueMono = managerService.addTaskToOrderByOrderId(orderId,taskId);
        StepVerifier.create(taskQueueMono)
                .thenConsumeWhile(result -> {
                    assertThat(result).isNotNull();
                    assertThat(result.getAssignmentDate()).isNotNull();
                    assertThat(result.getOrderId()).isEqualTo(1);
                    return true;
                })
                .verifyComplete();
    }
}
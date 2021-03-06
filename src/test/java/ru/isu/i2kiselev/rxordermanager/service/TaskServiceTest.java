package ru.isu.i2kiselev.rxordermanager.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.isu.i2kiselev.rxordermanager.model.Task;
import ru.isu.i2kiselev.rxordermanager.repository.TaskRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void saveReturnsSameTaskTest() {
        Task task = new Task();
        task.setTaskName("testTask");
        given(taskRepository.save(any(Task.class))).willReturn(Mono.just(task));
        Mono<Task> taskMono = taskService.save(task);
        StepVerifier.create(taskMono)
                .thenConsumeWhile(result -> {
                    assertThat(result).isNotNull();
                    assertThat(result.getTaskName()).isEqualTo(task.getTaskName());
                    return true;
                })
                .verifyComplete();
    }

    @Test
    void findAllReturnsFluxOfTasksTest() {
        List<Task> tasks = new ArrayList<>();
        Task task = new Task();
        task.setTaskName("test");
        Task task1 = new Task();
        task1.setTaskName("test1");
        tasks.add(task);
        tasks.add(task1);
        given(taskRepository.findAll()).willReturn(Flux.fromIterable(tasks));
        Flux<Task> taskFlux = taskService.findAll();
        StepVerifier.create(taskFlux)
                .expectNext(task, task1)
                .expectNextCount(0)
                .expectComplete()
                .verify();
    }

    @Test
    void findAllReturnsEmptyFluxTest() {
        List<Task> tasks = new ArrayList<>();
        given(taskRepository.findAll()).willReturn(Flux.fromIterable(tasks));
        Flux<Task> taskFlux = taskService.findAll();
        StepVerifier.create(taskFlux)
                .expectNextCount(0)
                .expectComplete()
                .verify();
    }

    @Test
    void findByIdReturnsMonoTest() {
        Task task = new Task();
        task.setTaskName("test");
        given(taskRepository.findById(any(Integer.class))).willReturn(Mono.just(task));
        Mono<Task> taskMono = taskService.findById(1);
        StepVerifier.create(taskMono)
                .expectNext(task)
                .expectNextCount(0)
                .expectComplete()
                .verify();
    }

    @Test
    void findAllByEmployeeIdReturnsFluxTest() {
        List<Task> tasks = new ArrayList<>();
        Task task = new Task();
        task.setTaskName("test");
        Task task1 = new Task();
        task1.setTaskName("test1");
        tasks.add(task);
        tasks.add(task1);
        given(taskRepository.findAllByEmployeeId(any(Integer.class))).willReturn(Flux.fromIterable(tasks));
        Flux<Task> taskFlux = taskService.findAllByEmployeeId(1);
        StepVerifier.create(taskFlux)
                .expectNext(task, task1)
                .expectNextCount(0)
                .expectComplete()
                .verify();
    }

    @Test
    void findAllByEmployeeIdReturnsEmptyFluxTest(){
        List<Task> tasks = new ArrayList<>();
        given(taskRepository.findAllByEmployeeId(any(Integer.class))).willReturn(Flux.fromIterable(tasks));
        Flux<Task> taskFlux = taskService.findAllByEmployeeId(1);
        StepVerifier.create(taskFlux)
                .expectNextCount(0)
                .expectComplete()
                .verify();
    }

    @Test
    void findAllNotAddedByEmployeeIdReturnsFluxTest() {
        List<Task> tasks = new ArrayList<>();
        Task task = new Task();
        task.setTaskName("test");
        Task task1 = new Task();
        task1.setTaskName("test1");
        tasks.add(task);
        tasks.add(task1);
        given(taskRepository.findAllNotAddedByEmployeeId(any(Integer.class))).willReturn(Flux.fromIterable(tasks));
        Flux<Task> taskFlux = taskService.findAllNotAddedByEmployeeId(1);
        StepVerifier.create(taskFlux)
                .expectNext(task, task1)
                .expectNextCount(0)
                .expectComplete()
                .verify();
    }

    @Test
    void findAllNotAddedByEmployeeIdReturnsEmptyFluxTest(){
        List<Task> tasks = new ArrayList<>();
        given(taskRepository.findAllNotAddedByEmployeeId(any(Integer.class))).willReturn(Flux.fromIterable(tasks));
        Flux<Task> taskFlux = taskService.findAllNotAddedByEmployeeId(1);
        StepVerifier.create(taskFlux)
                .expectNextCount(0)
                .expectComplete()
                .verify();
    }

    @Test
    void findAllByOrderIdReturnsFluxTest() {
        List<Task> tasks = new ArrayList<>();
        Task task = new Task();
        task.setTaskName("test");
        Task task1 = new Task();
        task1.setTaskName("test1");
        tasks.add(task);
        tasks.add(task1);
        given(taskRepository.findAllByOrderId(any(Integer.class))).willReturn(Flux.fromIterable(tasks));
        Flux<Task> taskFlux = taskService.findAllByOrderId(1);
        StepVerifier.create(taskFlux)
                .expectNext(task, task1)
                .expectNextCount(0)
                .expectComplete()
                .verify();
    }

}


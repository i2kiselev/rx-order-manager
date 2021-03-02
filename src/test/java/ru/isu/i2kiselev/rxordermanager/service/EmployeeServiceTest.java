package ru.isu.i2kiselev.rxordermanager.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.isu.i2kiselev.rxordermanager.model.Employee;
import ru.isu.i2kiselev.rxordermanager.repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void addEmployeeReturnsMonoEmployeeTest(){
        Employee employee = new Employee();
        employee.setUsername("Test name");
        given(employeeRepository.save(any(Employee.class))).willReturn(Mono.just(employee));
        Mono<Employee> employeeMono = employeeService.saveEmployee(employee);
        StepVerifier.create(employeeMono)
                    .thenConsumeWhile(result -> {
                                    assertThat(result).isNotNull();
                                    assertThat(result.getUsername()).isEqualTo(employee.getUsername());
                            return true;
                    })
                    .verifyComplete();
    }

    @Test
    void findByIdReturnsEmployeeMonoTest(){
        Employee employee = new Employee();
        employee.setUsername("Test name");
        given(employeeRepository.findById(any(Integer.class))).willReturn(Mono.just(employee));
        Mono<Employee> employeeMono = employeeService.findById(1);
        StepVerifier.create(employeeMono)
                .thenConsumeWhile(result -> {
                    assertThat(result).isNotNull();
                    assertThat(result.getUsername()).isEqualTo(employee.getUsername());
                    return true;
                })
                .verifyComplete();

    }

    @Test
    void deleteEmployeeInvokesRepositoryDeleteMethod(){
        given(employeeRepository.delete(any(Employee.class))).willReturn(Mono.when());
        employeeService.deleteEmployeeById(1);
        verify(employeeRepository).deleteById(1);
    }

    @Test
    void findAllReturnsFluxWithTwoEmployeesTest(){
        List<Employee> employees = new ArrayList<>();
        Employee employee1 = new Employee("user1");
        Employee employee2 = new Employee("user2");
        employees.add(employee1);
        employees.add(employee2);
        given(employeeRepository.findAll()).willReturn(Flux.fromIterable(employees));
        Flux<Employee> employeeFlux = employeeService.findAll();
        StepVerifier.create(employeeFlux)
                    .expectNext(employee1,employee2)
                    .expectNextCount(0)
                    .expectComplete()
                    .verify();
    }

    @Test
    void findAllReturnsFluxWithNoEmployeesTest(){
        List<Employee> employees = new ArrayList<>();
        given(employeeRepository.findAll()).willReturn(Flux.fromIterable(employees));
        Flux<Employee> employeeFlux = employeeService.findAll();
        StepVerifier.create(employeeFlux)
                .expectNextCount(0)
                .expectComplete()
                .verify();
    }

    @Test
    void findAllByTaskIdReturnsFluxWithNoEmployeesTest(){
        List<Employee> employees = new ArrayList<>();
        given(employeeRepository.findAllByTaskId(any(Integer.class))).willReturn(Flux.fromIterable(employees));
        Flux<Employee> employeeFlux = employeeService.findAllByTaskId(1);
        StepVerifier.create(employeeFlux)
                .expectNextCount(0)
                .expectComplete()
                .verify();
    }

    @Test
    void findAllByTaskIdReturnsFluxWithEmployeesTest(){
        List<Employee> employees = new ArrayList<>();
        Employee employee1 = new Employee("user1");
        Employee employee2 = new Employee("user2");
        employees.add(employee1);
        employees.add(employee2);
        given(employeeRepository.findAllByTaskId(any(Integer.class))).willReturn(Flux.fromIterable(employees));
        Flux<Employee> employeeFlux = employeeService.findAllByTaskId(1);
        StepVerifier.create(employeeFlux)
                .expectNext(employee1,employee2)
                .expectNextCount(0)
                .expectComplete()
                .verify();
    }

    @Test
    void addTaskEstimateReturnsMonoInteger(){
        List<Employee> employees = new ArrayList<>();
        given(employeeRepository.addTaskEstimateToEmployee(any(Integer.class),any(Integer.class),any(Integer.class))).willReturn(Mono.just(1));
        Mono<Integer> addTaskEstimate = employeeService.addTaskEstimate(1,1,1);
        StepVerifier.create(addTaskEstimate)
                .expectNextCount(1)
                .expectComplete()
                .verify();
    }

    @Test
    void updateTaskEstimateReturnsMonoInteger(){
        List<Employee> employees = new ArrayList<>();
        given(employeeRepository.updateTaskEstimateOfEmployee(any(Integer.class),any(Integer.class),any(Integer.class))).willReturn(Mono.just(1));
        Mono<Integer> updateTaskEstimate = employeeService.updateTaskEstimate(1,1,1);
        StepVerifier.create(updateTaskEstimate)
                .expectNextCount(1)
                .expectComplete()
                .verify();
    }

    @Test
    void deleteTaskEstimateReturnsMonoInteger(){
        List<Employee> employees = new ArrayList<>();
        given(employeeRepository.removeTaskEstimateToEmployee(any(Integer.class),any(Integer.class))).willReturn(Mono.just(1));
        Mono<Integer> deleteTaskEstimate = employeeService.deleteTaskEstimate(1,1);
        StepVerifier.create(deleteTaskEstimate)
                .expectNextCount(1)
                .expectComplete()
                .verify();
    }
}

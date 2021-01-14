package ru.isu.i2kiselev.rxordermanager.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.isu.i2kiselev.rxordermanager.model.Employee;
import ru.isu.i2kiselev.rxordermanager.repository.EmployeeRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;

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

    /*@Test
    void findAllReturnsFluxEmployeeTest(){
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("user1"));
        employees.add(new Employee("user2"));
        given(employeeRepository.findAll()).willReturn(Flux.fromIterable(employees));
        Flux<Employee> employeeFlux = employeeService.findAll();
        StepVerifier.create(employeeFlux)
                    .expectNext()

                .thenConsumeWhile(result -> {
                    assertThat(result).isNotNull();
                    assertThat(result.getUsername()).isEqualTo(employee.getUsername());
                    return true;
                })
                .verifyComplete();
    }*/
}

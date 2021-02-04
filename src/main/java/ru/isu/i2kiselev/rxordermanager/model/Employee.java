package ru.isu.i2kiselev.rxordermanager.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Employee POJO. It represent company's employee with array of tasks and respective completion estimates stored in RDBMS
 * @version 0.2
 * @author Ilya Kiselev
 */



@Data
@NoArgsConstructor
@Table("employee")
public class Employee {

    @Id
    private Integer id;

    private String username;

    public Employee(String username) {
        this.username = username;
    }

}

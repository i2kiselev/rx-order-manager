package ru.isu.i2kiselev.rxordermanager.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Task POJO. It represents tasks that can be completed by company's employees
 * @author Ilya Kiselev
 */

@Data
@Table("task")
public class Task {

    @Id
    private Integer id;

    private String taskName;

    private Integer defaultEstimate;
}

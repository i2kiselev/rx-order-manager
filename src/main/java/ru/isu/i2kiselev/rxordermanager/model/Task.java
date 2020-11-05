package ru.isu.i2kiselev.rxordermanager.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table
public class Task {
    @Id
    private Integer id;

    private String taskName;
}

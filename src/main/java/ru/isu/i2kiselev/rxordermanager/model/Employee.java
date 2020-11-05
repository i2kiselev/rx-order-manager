package ru.isu.i2kiselev.rxordermanager.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Collection;

@Getter
@Setter
@Table
public class Employee {

    @Id
    private Integer id;

    //Nickname of employee
    private String username;

    //Map of task IDs and their respective completion time
    //private Map<Integer, Integer> taskEstimates;

    private Collection<Integer> tasks;

    private Collection<Integer> estimates;

}

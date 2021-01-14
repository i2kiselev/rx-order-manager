package ru.isu.i2kiselev.rxordermanager.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Collection;

/**
 * Employee POJO. It represent company's employee with array of tasks and respective completion estimates
 * @version 0.1
 * @author Ilya Kiselev
 */


@Getter
@Setter
@Table
public class Employee {

    @Id
    private Integer id;

    //Nickname of employee
    private String username;

    //Task IDs
    private Collection<Integer> tasks;

    //Estimated task completion times
    private Collection<Integer> estimates;

    public void addTaskEstimate(Task task, Integer taskEstimate){
           this.getTasks().add(task.getId());
           this.getEstimates().add(taskEstimate);
    }
}

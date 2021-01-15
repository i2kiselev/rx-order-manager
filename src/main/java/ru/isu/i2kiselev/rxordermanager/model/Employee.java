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
@Table
public class Employee {

    @Id
    private Integer id;

    //Nickname of employee
    private String username;

    /*//Task IDs
    private Collection<Integer> tasks;

    //Estimated task completion times
    private Collection<Integer> estimates;

    public void addTaskEstimate(Task task, Integer taskEstimate){
           this.getTasks().add(task.getId());
           this.getEstimates().add(taskEstimate);
    }*/

    public Employee(String username) {
        this.username = username;
    }

}

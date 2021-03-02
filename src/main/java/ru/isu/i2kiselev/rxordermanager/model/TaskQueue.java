package ru.isu.i2kiselev.rxordermanager.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * TaskQueue POJO. It represents record task from order assigned to employee for completion
 * @version 0.6
 * @author Ilya Kiselev
 */

@Data
@Table("task_queue")
public class TaskQueue {

    @Id
    private Integer id;

    private Integer employeeId;

    private Integer taskId;

    private Integer orderId;

    private Status status;

    private LocalDateTime assignmentDate;

    private String feedback;

    public TaskQueue(Integer employeeId, Integer taskId, Integer orderId, Status status, LocalDateTime assignmentDate) {
        this.employeeId = employeeId;
        this.taskId = taskId;
        this.orderId = orderId;
        this.status = status;
        this.assignmentDate = assignmentDate;
    }

    public TaskQueue(Integer taskId, Integer orderId, Status status, LocalDateTime assignmentDate) {
        this.taskId = taskId;
        this.orderId = orderId;
        this.status = status;
        this.assignmentDate = assignmentDate;
    }

    public TaskQueue() {

    }
}

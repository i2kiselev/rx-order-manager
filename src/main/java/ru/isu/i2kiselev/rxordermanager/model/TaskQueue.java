package ru.isu.i2kiselev.rxordermanager.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * TaskQueue POJO. It represents record of employee taking some task for completion
 * @version 0.2
 * @author Ilya Kiselev
 */

@Data
public class TaskQueue {

    private Integer employeeId;

    private Integer taskId;

    private Integer orderId;

    private Status status;

    private LocalDateTime assignmentDate;

}

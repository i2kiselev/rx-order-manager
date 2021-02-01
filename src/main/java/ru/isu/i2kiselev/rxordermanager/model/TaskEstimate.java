package ru.isu.i2kiselev.rxordermanager.model;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Task estimate POJO.
 * @version 0.1
 * @author Ilya Kiselev
 */

@Data
@Table
public class TaskEstimate {
    private Integer taskId;

    private Integer employeeId;

    private Integer estimate;

    private Status taskStatus;
}

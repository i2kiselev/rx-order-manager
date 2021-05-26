package ru.isu.i2kiselev.rxordermanager.model;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Task estimate POJO.
 * @author Ilya Kiselev
 */

@Data
@Table
public class  TaskEstimate {

    private Integer taskId;

    private Integer employeeId;

    private Integer estimate;

}

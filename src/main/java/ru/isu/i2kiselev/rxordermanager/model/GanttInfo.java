package ru.isu.i2kiselev.rxordermanager.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

/*
DTO for transforming into final GanttElement objects
 */

@Data
public class GanttInfo {

    private String id;

    private String text;

    private LocalDateTime orderCreationDate;

    private LocalDateTime taskAssignmentDate;

    private LocalDateTime taskStartDate;

    private LocalDateTime taskFinishDate;

    private LocalDateTime startDate;

    private Double employeeDuration;

    private Double defaultDuration;

    private Double duration;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer orderId;

    private String orderName;


}

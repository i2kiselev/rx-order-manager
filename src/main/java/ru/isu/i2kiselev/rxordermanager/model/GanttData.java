package ru.isu.i2kiselev.rxordermanager.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GanttData {
    private Integer id;

    private String taskName;

    private LocalDateTime startDate;

    private Double duration;

}

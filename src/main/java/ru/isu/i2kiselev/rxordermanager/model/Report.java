package ru.isu.i2kiselev.rxordermanager.model;

import lombok.Data;

@Data
public class Report {

    private String taskName;

    private String employeeName;

    private String feedback;

    private Double duration;

    private Double estimateDuration;

    private String orderName;

}

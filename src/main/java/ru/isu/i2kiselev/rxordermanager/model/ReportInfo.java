package ru.isu.i2kiselev.rxordermanager.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReportInfo {

    private LocalDateTime dateOfReport;

    private Integer tasksFinishedInTime;

    private Integer tasksNotFinishedInTime;

}

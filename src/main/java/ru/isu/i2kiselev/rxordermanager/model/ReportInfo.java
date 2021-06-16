package ru.isu.i2kiselev.rxordermanager.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ReportInfo {

    private LocalDate dateOfReport;

    private Integer tasksFinishedInTime;

    private Integer tasksNotFinishedInTime;

    private Integer tasksInProgress;

}

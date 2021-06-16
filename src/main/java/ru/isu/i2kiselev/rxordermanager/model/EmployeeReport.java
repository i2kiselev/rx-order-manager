package ru.isu.i2kiselev.rxordermanager.model;

import lombok.Data;

@Data
public class EmployeeReport {

    private String username;

    private Integer tasksFinishedInTime;

    private Integer tasksInProgress;

    private Integer tasksNotFinishedInTime;
}

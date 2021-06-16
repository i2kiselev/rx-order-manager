package ru.isu.i2kiselev.rxordermanager.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class OrderReport {

    private Integer orderId;

    private String orderName;

    private LocalDate currentDay;

    private Integer tasksFinishedInTime;

    private Integer tasksInProgress;

    private Integer tasksNotFinishedInTime;

}

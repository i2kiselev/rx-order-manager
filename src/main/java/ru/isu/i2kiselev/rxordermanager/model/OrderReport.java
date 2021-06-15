package ru.isu.i2kiselev.rxordermanager.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderReport {

    private Integer orderId;

    private String orderName;

    private LocalDateTime time;

    private Integer tasksFinishedInTime;

    private Integer taskNotFinishedInTime;

}

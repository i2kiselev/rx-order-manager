package ru.isu.i2kiselev.rxordermanager.model;

import org.springframework.data.annotation.Id;

import java.util.Deque;

public class TaskQueue {
    @Id
    private Integer id;

    private Task task;

}

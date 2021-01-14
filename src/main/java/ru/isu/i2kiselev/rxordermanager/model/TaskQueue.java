package ru.isu.i2kiselev.rxordermanager.model;

import org.springframework.data.annotation.Id;

/**
 * TaskQueue POJO. It represents record of employee taking some task for completion
 * @version 0.1
 * @author Ilya Kiselev
 */

public class TaskQueue {
    @Id
    private Integer id;

    private Task task;

}

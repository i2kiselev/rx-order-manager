package ru.isu.i2kiselev.rxordermanager.model;

import org.springframework.data.annotation.Id;

import java.util.Collection;

public class Order {
    @Id
    private Integer id;

    //Collection of task IDs
    private Collection<Integer> tasks;

}

package ru.isu.i2kiselev.rxordermanager.model;

import lombok.Data;

@Data
public class GanttParent {

    private Integer id;

    private String text;

    public GanttParent(Integer id, String text) {
        this.id = id;
        this.text = text;
    }
}

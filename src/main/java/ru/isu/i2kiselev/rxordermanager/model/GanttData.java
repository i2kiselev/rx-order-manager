package ru.isu.i2kiselev.rxordermanager.model;

import lombok.Data;

import java.util.List;

@Data
public class GanttData {

    List<GanttElement> data;

    public GanttData(List<GanttElement> tasks) {
        this.data = tasks;
    }
}

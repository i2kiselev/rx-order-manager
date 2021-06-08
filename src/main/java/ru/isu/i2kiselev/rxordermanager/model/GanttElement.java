package ru.isu.i2kiselev.rxordermanager.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.format.DateTimeFormatter;
import java.util.Random;

/*
DTO used to generate data for gantt script.
 */
@Data
public class GanttElement {

    private String id;

    private String text;

    private String start_date;

    private Double duration;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer parent;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private boolean open;

    private static int hash = 1000000;
    public GanttElement(GanttInfo ganttInfo){
        this.id = ganttInfo.getId();
        this.text = ganttInfo.getText();
        this.parent = ganttInfo.getOrderId()+hash;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.start_date = ganttInfo.getStartDate().format(formatter);
        this.duration = ganttInfo.getDuration();
    }

    public GanttElement(GanttParent ganttParent){
        this.id = String.valueOf(ganttParent.getId()+hash);
        this.text = ganttParent.getText();
        this.open = true;
    }
}

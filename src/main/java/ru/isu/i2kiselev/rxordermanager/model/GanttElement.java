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
    private Double parent;

    public GanttElement(GanttInfo ganttInfo){
        //Random random = new Random(ganttInfo.getOrderId().longValue());
        this.id = ganttInfo.getId();
        this.text = ganttInfo.getText();
        //this.parent = (double) (random.nextInt(100) * 10);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");
        this.start_date = ganttInfo.getStartDate().format(formatter);
        this.duration = ganttInfo.getDuration();
    }
}

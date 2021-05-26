package ru.isu.i2kiselev.rxordermanager.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class GanttElement {
    private String id;

    private String text;

    private String start_date;

    private Double duration;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double parent;
}

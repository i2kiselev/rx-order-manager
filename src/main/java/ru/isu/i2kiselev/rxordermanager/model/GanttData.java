package ru.isu.i2kiselev.rxordermanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class GanttData {

    List<GanttElement> data = new ArrayList<>();

}

package ru.isu.i2kiselev.rxordermanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

@Data
public class GanttData {

    List<GanttElement> data;

    public GanttData(List<GanttInfo> source){
        boolean anyoneIsAssigned = source.stream().anyMatch(x->x.getTaskAssignmentDate()!=null);
        if (anyoneIsAssigned){
            List<GanttInfo> finalData = new ArrayList<>();
            finalData.addAll(source.stream()
                    .filter(x->x.getTaskAssignmentDate()!=null)
                    .sorted(Comparator.comparing(GanttInfo::getTaskAssignmentDate).reversed())
                    .map(x-> {
                        if (x.getEmployeeDuration()!=null){
                            x.setDuration(x.getEmployeeDuration());
                        }
                        else {
                            x.setDuration(x.getDefaultDuration());
                        }
                        return x;
                    })
                    .collect(Collectors.toList()));
            finalData.addAll(source.stream()
                    .filter(x->x.getTaskAssignmentDate()==null)
                    .map(x-> {
                        if (x.getEmployeeDuration()!=null){
                            x.setDuration(x.getEmployeeDuration());
                        }
                        else {
                            x.setDuration(x.getDefaultDuration());
                        }
                        return x;
                    })
                    .collect(Collectors.toList()));
            this.data = new ArrayList<>();
            GanttInfo first = finalData.get(0);
            first.setStartDate(first.getTaskAssignmentDate());
            this.data.add(new GanttElement(finalData.get(0)));
            for (int i = 1;i<finalData.size();i++) {
                GanttInfo ganttInfo = finalData.get(i);
                GanttInfo prevGanttInfo = finalData.get(i-1);
                ganttInfo.setStartDate(prevGanttInfo.getStartDate().plusHours(prevGanttInfo.getDuration().longValue()));
                this.data.add(new GanttElement(ganttInfo));
            }

        }
    }

}

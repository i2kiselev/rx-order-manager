package ru.isu.i2kiselev.rxordermanager.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/*
DTO for transforming into final GanttElement objects
 */

@Data
public class GanttInfo {

    private String id;

    private String text;

    private LocalDateTime orderCreationDate;

    private LocalDateTime taskAssignmentDate;

    private LocalDateTime taskStartDate;

    private LocalDateTime taskFinishDate;

    private LocalDateTime startDate;

    private Double employeeDuration;

    private Double defaultDuration;

    private Double duration;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer orderId;

    private String orderName;
    
    public static GanttInfo setStartDateAndDurationForAssigned(GanttInfo ganttInfo) {
        ganttInfo.setStartDate(ganttInfo.getTaskAssignmentDate());
        ganttInfo.setDuration(ganttInfo.getEmployeeDuration());
        return ganttInfo;
    }

    public static GanttInfo setStartDateAndDurationForUnassigned(GanttInfo ganttInfo) {
        ganttInfo.setStartDate(ganttInfo.getOrderCreationDate());
        ganttInfo.setDuration(ganttInfo.getDefaultDuration());
        return ganttInfo;
    }

    public static GanttInfo setStartDateAndDurationForFinished(GanttInfo ganttInfo) {
        ganttInfo.setStartDate(ganttInfo.getTaskStartDate());
        ganttInfo.setDuration(getDateDiffInHours(ganttInfo.getTaskStartDate(),ganttInfo.getTaskFinishDate()));
        return ganttInfo;
    }
    
    private static double getDateDiffInHours(LocalDateTime from, LocalDateTime to){
        long hours = from.until(to, ChronoUnit.HOURS);
        from = from.plusHours(hours);
        long minutes = from.until(to,ChronoUnit.MINUTES);
        if (hours!=0){
            return hours+minutes/60.;

        }
        return 1;
    }
}

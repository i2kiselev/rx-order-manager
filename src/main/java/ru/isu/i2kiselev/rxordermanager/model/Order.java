package ru.isu.i2kiselev.rxordermanager.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Order POJO
 * @author Ilya Kiselev
 */

@Data
@Table("order_table")
public class Order {

    @Id
    private Integer id;

    private String description;

    private LocalDateTime creationDate;

    @Transient
    private List<Integer> ids;

    @Transient
    private List<Integer> quantities;

    @Transient
    private List<Integer> tasks;
}

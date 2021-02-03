package ru.isu.i2kiselev.rxordermanager.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.List;

/**
 * Order POJO
 * @version 0.5
 * @author Ilya Kiselev
 */

@Data
@Table
public class Order {

    @Id
    private Integer id;

    private String description;

    private LocalDate creationDate;

    @Transient
    private List<Integer> ids;

    @Transient
    private List<Integer> quantities;

}

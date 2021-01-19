package ru.isu.i2kiselev.rxordermanager.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Order POJO
 * @version 0.2
 * @author Ilya Kiselev
 */

@Data
@Table
public class Order {
    @Id
    private Integer id;

}

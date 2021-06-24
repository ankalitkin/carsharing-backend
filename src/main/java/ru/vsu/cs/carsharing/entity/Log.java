package ru.vsu.cs.carsharing.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "log")
@Data
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "type")
    private String type;

    @Column(name = "customer_id")
    private int customerId;

    @Column(name = "car_id")
    private int carId;

    @Column(name = "value")
    private String value;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;
}

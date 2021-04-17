package ru.vsu.cs.carsharing.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "document")
@Data
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "customer_id")
    private int customerId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birthdate")
    private Date birthdate;

    @Column(name = "issued_on")
    private Date issuedAt;

    @Column(name = "valid_by")
    private Date validBy;

    @Column(name = "driving_exp_since")
    private Date drivingExpSince;

    @Column(name = "photo_filename")
    private String photoFilename;

}

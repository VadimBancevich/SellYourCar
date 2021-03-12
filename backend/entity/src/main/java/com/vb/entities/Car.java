package com.vb.entities;


import com.vb.entities.enums.*;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "car")
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class Car extends AEntity {

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "car_condition", nullable = false)
    @Enumerated(value = EnumType.STRING)
    @ColumnTransformer(forColumn = "car_condition", read = "UPPER(car_condition)", write = "LOWER(?)")
    private Condition condition;

    @Column(name = "mileage", nullable = false)
    private Integer mileage;

    @Column(name = "transmission", nullable = false)
    @Enumerated(value = EnumType.STRING)
    @ColumnTransformer(forColumn = "transmission", read = "UPPER(transmission)", write = "LOWER(?)")
    private Transmission transmission;

    @Column(name = "drivetrain", nullable = false)
    @Enumerated(value = EnumType.STRING)
    @ColumnTransformer(forColumn = "drivetrain", read = "UPPER(drivetrain)", write = "LOWER(?)")
    private Drivetrain drivetrain;

    @Column(name = "body_type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    @ColumnTransformer(forColumn = "body_type", read = "UPPER(body_type)", write = "LOWER(?)")
    private BodyType bodyType;

    @Column(name = "vin", length = 45)
    private String vin;

    @Column(name = "engine", nullable = false)
    @Enumerated(value = EnumType.STRING)
    @ColumnTransformer(forColumn = "engine", read = "UPPER(engine)", write = "LOWER(?)")
    private Engine engine;

    @Column(name = "engine_volume", nullable = false)
    private Integer engineVolume;

    @Column(name = "status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    @ColumnTransformer(forColumn = "status", read = "UPPER(status)", write = "LOWER(?)")
    private CarStatus status;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "gas")
    @Enumerated(value = EnumType.STRING)
    @ColumnTransformer(forColumn = "gas", read = "UPPER(gas)", write = "LOWER(?)")
    private Gas gas;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "model_id", referencedColumnName = "id")
    private Model model;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "generation_id", referencedColumnName = "id")
    private Generation generation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "phone")
    private String phone;

    @Column(name = "seller_name")
    private String sellerName;

    @Column(name = "description")
    private String description;

    @Column(name = "creating_date", nullable = false)
    private LocalDate creatingDate;

    @Column(name = "upping_date", nullable = false)
    private LocalDate uppingDate;

    @Type(type = "json")
    @Column(name = "images", columnDefinition = "json")
    private List<String> images;

}

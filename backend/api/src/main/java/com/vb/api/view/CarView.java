package com.vb.api.view;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class CarView extends AView {

    private Integer year;
    private String condition;
    private Integer mileage;
    private String transmission;
    private String drivetrain;
    private String bodyType;
    private String vin;
    private String engine;
    private Integer engineVolume;
    private String gas;
    private String brand;
    private String model;
    private String generation;
    private LocalDate creatingDate;
    private LocalDate uppingDate;
    private Integer price;
    private Long userId;
    private String sellerName;
    private String status;
    private List<String> images;
    private String phone;
    private String description;

}

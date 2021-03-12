package com.vb.api.dto;

import com.vb.entities.enums.*;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class CarDto extends ADto {

    @NotBlank
    private Long brandId;
    @NotBlank
    private Long modelId;
    @NotBlank
    private Long generationId;
    @Hidden
    private Long userId;
    private String sellerName;
    @NotBlank
    private Integer year;
    @NotBlank
    private Condition condition;
    @NotBlank
    private Integer mileage;
    @NotBlank
    private Transmission transmission;
    @NotBlank
    private Drivetrain drivetrain;
    @NotBlank
    private BodyType bodyType;
    private String vin;
    @NotBlank
    private Engine engine;
    @NotBlank
    private Integer engineVolume;
    private Gas gas;
    @NotBlank
    private Integer price;
    @Hidden
    private String phone;
    private String description;
    @Hidden
    private CarStatus status;
    @Hidden
    private LocalDate creatingDate;
    @Hidden
    private LocalDate uppingDate;
    @Hidden
    private List<String> images;

}

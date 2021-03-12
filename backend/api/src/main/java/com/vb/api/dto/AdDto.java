package com.vb.api.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class AdDto extends ADto {

    private LocalDate creatingDate;
    private LocalDate uppingDate;
    private Long userId;
    private String status;
    private String image1;
    private String image2;
    private String image3;
    private String image4;
    private String image5;
    private String telNumber;

}

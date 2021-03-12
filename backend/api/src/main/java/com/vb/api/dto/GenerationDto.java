package com.vb.api.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GenerationDto extends ADto {

    private String generationName;

}

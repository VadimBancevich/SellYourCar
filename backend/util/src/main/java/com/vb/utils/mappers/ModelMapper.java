package com.vb.utils.mappers;

import com.vb.api.dto.ModelDto;
import com.vb.entities.Model;
import org.springframework.stereotype.Component;

@Component("mapperForModelEntity")
public class ModelMapper extends AEntityDtoMapper<Model, ModelDto> {

    public ModelMapper() {
        super(Model.class, ModelDto.class);
    }

}

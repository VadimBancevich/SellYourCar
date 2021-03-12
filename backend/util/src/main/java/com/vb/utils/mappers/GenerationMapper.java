package com.vb.utils.mappers;

import com.vb.api.dto.GenerationDto;
import com.vb.entities.Generation;
import org.springframework.stereotype.Component;

@Component
public class GenerationMapper extends AEntityDtoMapper<Generation, GenerationDto> {

    public GenerationMapper() {
        super(Generation.class, GenerationDto.class);
    }
    
}

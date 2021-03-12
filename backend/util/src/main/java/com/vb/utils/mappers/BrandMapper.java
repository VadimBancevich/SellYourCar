package com.vb.utils.mappers;

import com.vb.api.dto.BrandDto;
import com.vb.entities.Brand;
import org.springframework.stereotype.Component;

@Component
public class BrandMapper extends AEntityDtoMapper<Brand, BrandDto> {

    public BrandMapper() {
        super(Brand.class, BrandDto.class);
    }

}

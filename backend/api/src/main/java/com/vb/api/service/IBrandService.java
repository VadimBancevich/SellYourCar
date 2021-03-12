package com.vb.api.service;

import com.vb.api.dto.BrandDto;
import com.vb.entities.Brand;

import java.util.List;

public interface IBrandService extends IBaseService<Brand, BrandDto> {

    List<BrandDto> getAllBrands();

}

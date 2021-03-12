package com.vb.api.service;


import com.vb.api.dto.ModelDto;

import java.util.List;

public interface IModelService {

    List<ModelDto> getAllModelByBrandId(Long brandId);

    ModelDto findDtoById(Long id);

}

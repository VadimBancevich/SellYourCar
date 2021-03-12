package com.vb.api.service;

import com.vb.api.dto.GenerationDto;
import com.vb.entities.Generation;

import java.util.List;

public interface IGenerationService {

    List<GenerationDto> getAllGenerationsByModelId(Long modelId);

    GenerationDto findDtoById(Long id);

    Generation findWithBrandAndModel(Long id);

}

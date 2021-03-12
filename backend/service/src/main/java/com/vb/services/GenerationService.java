package com.vb.services;

import com.vb.api.dao.IGenerationDao;
import com.vb.api.dto.GenerationDto;
import com.vb.api.service.IGenerationService;
import com.vb.entities.Generation;
import com.vb.utils.mappers.GenerationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class GenerationService implements IGenerationService {

    @Autowired
    private GenerationMapper generationMapper;

    @Autowired
    private IGenerationDao generationDao;

    @Override
    @Cacheable("generations")
    public List<GenerationDto> getAllGenerationsByModelId(Long modelId) {
        return generationMapper.toDtoList(generationDao.findGenerationsByModel_Id(modelId));
    }

    @Override
    @Cacheable("generation")
    public GenerationDto findDtoById(Long id) {
        return generationMapper.toDto(generationDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Generation with that id not found")));
    }

    @Override
    public Generation findWithBrandAndModel(Long id) {
        return generationDao.findWithBrandAndModelById(id).
                orElseThrow(() -> new EntityNotFoundException("Generation with that id not found"));
    }

}

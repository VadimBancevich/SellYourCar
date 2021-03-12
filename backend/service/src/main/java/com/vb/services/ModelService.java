package com.vb.services;

import com.vb.api.dao.IModelDao;
import com.vb.api.dto.ModelDto;
import com.vb.api.service.IModelService;
import com.vb.utils.mappers.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ModelService implements IModelService {

    @Autowired
    private IModelDao modelDao;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Cacheable("models")
    public List<ModelDto> getAllModelByBrandId(Long brandId) {
        return modelMapper.toDtoList(modelDao.findModelsByBrand_Id(brandId));
    }

    @Override
    @Cacheable("model")
    public ModelDto findDtoById(Long id) {
        return modelMapper.toDto(modelDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Model with that id not found")));
    }

}

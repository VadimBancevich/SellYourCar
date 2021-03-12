package com.vb.services;

import com.vb.api.dao.IBrandDao;
import com.vb.api.dto.BrandDto;
import com.vb.api.service.IBrandService;
import com.vb.entities.Brand;
import com.vb.utils.mappers.BrandMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class BrandService implements IBrandService {

    @Autowired
    private IBrandDao brandDao;

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public Brand findById(Long id) {
        return brandDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Brand with that id not found"));
    }

    @Override
    @Cacheable("brands")
    public List<BrandDto> getAllBrands() {
        return brandMapper.toDtoList(brandDao.findAll());
    }

    @Override
    @Cacheable("brand")
    public BrandDto findDtoById(Long id) {
        return brandMapper.toDto(findById(id));
    }

}

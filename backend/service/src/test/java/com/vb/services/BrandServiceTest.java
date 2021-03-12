package com.vb.services;

import com.vb.api.dao.IBrandDao;
import com.vb.api.dto.BrandDto;
import com.vb.api.service.IBrandService;
import com.vb.entities.Brand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.vb.ApplicationTest.existEntityId;
import static com.vb.ApplicationTest.notExistEntityId;
import static org.junit.Assert.*;

@SpringBootTest
class BrandServiceTest {

    @MockBean
    private IBrandDao brandDao;

    @Autowired
    private IBrandService brandService;

    @BeforeEach
    void init() {
        Mockito.when(brandDao.findById(existEntityId)).thenReturn(Optional.of(new Brand()));
        Mockito.when(brandDao.findById(notExistEntityId)).thenReturn(Optional.empty());
    }


    @Test
    void findByIdTest() {
        Brand testBrand = brandService.findById(existEntityId);

        assertNotNull(testBrand);
        assertThrows(EntityNotFoundException.class, () -> brandService.findById(notExistEntityId));
    }

    @Test
    void getAllBrands() {
        int brandsCount = 10;
        List<Brand> brands = Stream.generate(Brand::new).limit(brandsCount).collect(Collectors.toList());
        Mockito.when(brandDao.findAll()).thenReturn(brands);

        List<BrandDto> testBrands = brandService.getAllBrands();
        assertEquals(brandsCount, testBrands.size());
    }

    @Test
    void findDtoById() {
        BrandDto testBrandDto = brandService.findDtoById(existEntityId);

        assertNotNull(testBrandDto);
        assertThrows(EntityNotFoundException.class, () -> brandService.findDtoById(notExistEntityId));
    }

}
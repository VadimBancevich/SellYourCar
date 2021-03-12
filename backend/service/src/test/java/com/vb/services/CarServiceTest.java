package com.vb.services;

import com.vb.api.dao.IBrandDao;
import com.vb.api.dao.ICarDao;
import com.vb.api.dao.IGenerationDao;
import com.vb.api.dao.IModelDao;
import com.vb.api.dao.impl.LightPage;
import com.vb.api.dto.CarDto;
import com.vb.api.service.ICarService;
import com.vb.api.service.IGenerationService;
import com.vb.api.service.IStorageService;
import com.vb.api.service.IUserService;
import com.vb.api.view.CarView;
import com.vb.entities.*;
import com.vb.utils.mappers.CarMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Optional;

import static com.vb.ApplicationTest.existEntityId;
import static com.vb.ApplicationTest.notExistEntityId;
import static org.junit.Assert.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class CarServiceTest {

    @MockBean
    private ICarDao carDao;
    @MockBean
    private IBrandDao brandDao;
    @MockBean
    private IModelDao modelDao;
    @MockBean
    private IGenerationDao generationDao;
    @MockBean
    private IGenerationService generationService;
    @MockBean
    private IUserService userService;
    @MockBean
    private IStorageService storageService;
    @MockBean
    private CarMapper carMapper;

    @Autowired
    private ICarService carService;

    @BeforeEach
    void init() {
        when(carDao.findById(existEntityId)).thenReturn(Optional.of(new Car()));
        when(carDao.findById(notExistEntityId)).thenReturn(Optional.empty());
        when(carDao.findByParams(any(MultiValueMap.class), any(Pageable.class)))
                .thenReturn(new LightPage<>(new ArrayList<>(), 1, 0, 10));
        when(carMapper.toDto(any(Car.class))).thenReturn(new CarDto());
        when(carMapper.toView(any(Car.class))).thenReturn(new CarView());
    }


    @Test
    void findByIdTest() {
        Car testCar = carService.findById(existEntityId);
        assertNotNull(testCar);
        assertThrows(EntityNotFoundException.class, () -> carService.findById(notExistEntityId));
    }

    @Test
    void findDtoByIdTest() {
        Car car = new Car();
        car.setBrand(new Brand());
        car.setModel(new Model());
        car.setGeneration(new Generation());
        car.setUser(new User());
        when(carDao.findById(existEntityId)).thenReturn(Optional.of(car));

        CarDto testCar = carService.findDtoById(existEntityId);
        assertNotNull(testCar);
        assertThrows(EntityNotFoundException.class, () -> carService.findDtoById(notExistEntityId));

    }

    @Test
    void findByParamsTest() {
        LightPage<Car> testCarsPage = carService.findByParams(new LinkedMultiValueMap<>(), 1, 10);
        assertNotNull(testCarsPage);
    }

    @Test
    void findActiveCarsByParams() {
        findByParamsTest();
    }

    @Test
    void findViewsByParams() {
        LightPage<CarView> views = carService.findViewsByParams(new LinkedMultiValueMap<>(), 1, 10);
        assertNotNull(views);
    }

    @Test
    void findViewByIdTest() {
        CarView testCarView = carService.findViewById(existEntityId);
        assertNotNull(testCarView);
    }

}
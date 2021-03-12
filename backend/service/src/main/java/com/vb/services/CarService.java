package com.vb.services;

import com.vb.api.dao.ICarDao;
import com.vb.api.dao.impl.LightPage;
import com.vb.api.dto.CarDto;
import com.vb.api.service.ICarService;
import com.vb.api.service.IGenerationService;
import com.vb.api.service.IStorageService;
import com.vb.api.service.IUserService;
import com.vb.api.view.CarView;
import com.vb.entities.AEntity_;
import com.vb.entities.Car;
import com.vb.entities.Generation;
import com.vb.entities.enums.CarStatus;
import com.vb.utils.mappers.CarMapper;
import com.vb.utils.validators.CarDataValidator;
import com.vb.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class CarService implements ICarService {

    @Autowired
    private ICarDao carDao;

    @Autowired
    private CarMapper carMapper;

    @Autowired
    private IGenerationService generationService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IStorageService storageService;

    @Override
    public Car findById(Long id) {
        return carDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Car with that id not found"));
    }

    @Override
    public CarDto findDtoById(Long id) {
        return carMapper.toDto(findById(id));
    }

    @Override
    public LightPage<Car> findByParams(MultiValueMap<String, String> params, Integer page, Integer size) {
        return carDao.findByParams(params, PageRequest.of(page - 1, size));
    }

    @Override
    public LightPage<Car> findActiveCarsByParams(MultiValueMap<String, String> params, Integer pageNumber, Integer pageSize) {
        params.put("status", Collections.singletonList(CarStatus.ACTIVE.toString()));
        return findByParams(params, pageNumber, pageSize);
    }

    @Override
    public LightPage<CarView> findViewsByParams(MultiValueMap<String, String> params, Integer pageNumber, Integer pageSize) {
        LightPage<Car> entitiesPage = findActiveCarsByParams(params, pageNumber, pageSize);

        return new LightPage<>(carMapper.toViewList(entitiesPage.getContent()), entitiesPage.getPageNumber(),
                entitiesPage.getPageSize(), entitiesPage.getTotal());
    }

    @Override
    public CarView findViewById(Long id) {
        return carMapper.toView(findById(id));
    }

    @Override
    public void saveCar(CarDto carDto, List<MultipartFile> images) {
        CarDataValidator.validateCarImages(images);
        Car car = carMapper.toEntity(carDto);
        User user = userService.findPrincipal();
        car.setUser(user);
        Generation generation = generationService.findWithBrandAndModel(carDto.getGenerationId());
        car.setBrand(generation.getModel().getBrand());
        car.setModel(generation.getModel());
        car.setGeneration(generation);
        car.setCreatingDate(LocalDate.now(ZoneId.of(ZoneOffset.UTC.getId())));
        car.setUppingDate(LocalDate.now(ZoneId.of(ZoneOffset.UTC.getId())));
        car.setStatus(CarStatus.ACTIVE);
        car.setImages(new ArrayList<>(images.size()));
        carDao.save(car);

        if (!images.isEmpty()) {
            storageService.saveCarImages(car.getId(), images, car.getImages());
        }
    }

    @Override
    public CarView updateCar(CarDto carDto) {
        if (carDto.getId() == null) {
            throw new IllegalArgumentException("Car id must be present");
        }
        Car car = findPrincipalCar(carDto.getId());
        carMapper.toEntity(carDto, car);
        return carMapper.toView(carDao.save(car));
    }

    @Override
    public Car findPrincipalCar(Long carId) {
        return carDao.findPrincipalCarById(carId, userService.getPrincipalEmail())
                .orElseThrow(() -> new EntityNotFoundException("No car for this user"));
    }

    @Override
    public CarDto findPrincipalCarDto(Long carId) {
        return carMapper.toDto(findPrincipalCar(carId));
    }

    @Override
    public void deleteImage(Long carId, String imageUrl) {
        boolean isDeletedUrlFromDB = findPrincipalCar(carId).getImages().remove(imageUrl);
        boolean isDeletedImageFromBucket = storageService.deleteImage(imageUrl);
        if (!isDeletedUrlFromDB || !isDeletedImageFromBucket) {
            throw new IllegalArgumentException("Can`t find imageUrl in database or in storage");
        }
    }

    @Override
    public void addImage(Long carId, MultipartFile image) {
        CarDataValidator.validateCarImage(image);
        List<String> images = findPrincipalCar(carId).getImages();
        if (images.size() + 1 > 5) {
            throw new IllegalArgumentException("Images count for car must be <= 5");
        }
        storageService.saveCarImage(carId, image, images);
    }


    @Override
    public List<CarView> findNotActiveCars() {
        Pageable pageable = PageRequest.of(0, 30, Sort.by(AEntity_.ID));
        return carMapper.toViewList(carDao.findNotActiveCars(pageable));
    }

    @Override
    public void activateCar(Long carId) {
        findById(carId).setStatus(CarStatus.ACTIVE);
    }

    @Override
    public List<CarView> findPrincipalCars() {
        return carMapper.toViewList(
                carDao.findByUser_EmailOrderByUppingDateDesc(userService.getPrincipalEmail()));
    }

}
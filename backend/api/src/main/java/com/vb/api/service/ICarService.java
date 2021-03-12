package com.vb.api.service;

import com.vb.api.dao.impl.LightPage;
import com.vb.api.dto.CarDto;
import com.vb.api.view.CarView;
import com.vb.entities.Car;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ICarService extends IBaseService<Car, CarDto> {

    LightPage<Car> findActiveCarsByParams(MultiValueMap<String, String> params, Integer pageNumber, Integer pageSize);

    LightPage<Car> findByParams(MultiValueMap<String, String> params, Integer pageNumber, Integer pageSize);

    LightPage<CarView> findViewsByParams(MultiValueMap<String, String> params, Integer pageNumber, Integer pageSize);

    CarView findViewById(Long id);

    void saveCar(CarDto carDto, List<MultipartFile> images);

    CarView updateCar(CarDto carDto);

    Car findPrincipalCar(Long carId);

    CarDto findPrincipalCarDto(Long carId);

    void deleteImage(Long carId, String imageUrl);

    void addImage(Long carId, MultipartFile image);

    List<CarView> findNotActiveCars();

    void activateCar(Long carId);

    List<CarView> findPrincipalCars();

}

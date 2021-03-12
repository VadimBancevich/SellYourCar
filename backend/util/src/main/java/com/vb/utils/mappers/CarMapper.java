package com.vb.utils.mappers;

import com.vb.api.dto.CarDto;
import com.vb.api.service.IBrandService;
import com.vb.api.service.IGenerationService;
import com.vb.api.service.IModelService;
import com.vb.api.view.CarView;
import com.vb.entities.AEntity;
import com.vb.entities.Car;
import com.vb.entities.Generation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CarMapper extends AEntityDtoViewMapper<Car, CarDto, CarView> {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private IBrandService brandService;
    @Autowired
    private IModelService modelService;
    @Autowired
    private IGenerationService generationService;

    public CarMapper() {
        super(Car.class, CarDto.class, CarView.class);
    }

    @PostConstruct
    private void setupMap() {
        modelMapper.createTypeMap(Car.class, CarDto.class)
                .setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(Car.class, CarView.class)
                .addMappings(mapping -> {
                    mapping.skip(CarView::setBrand);
                    mapping.skip(CarView::setModel);
                    mapping.skip(CarView::setGeneration);
                }).setPostConverter(toViewConverter());
        modelMapper.createTypeMap(CarDto.class, Car.class)
                .addMappings(mapping -> {
                    mapping.skip(AEntity::setId);
                    mapping.skip(Car::setBrand);
                    mapping.skip(Car::setModel);
                    mapping.skip(Car::setGeneration);
                    mapping.skip(Car::setUser);
                    mapping.skip(Car::setImages);
                    mapping.skip(Car::setCreatingDate);
                    mapping.skip(Car::setUppingDate);
                    mapping.skip(Car::setStatus);
                })
                .setPostConverter(toEntityConverter());
    }

    @Override
    void mapSpecificFields(Car source, CarDto destination) {
        destination.setBrandId(source.getBrand().getId());
        destination.setModelId(source.getModel().getId());
        destination.setGenerationId(source.getGeneration().getId());
        destination.setUserId(source.getUser().getId());

    }

    @Override
    void mapSpecificFields(Car source, CarView destination) {
        destination.setUserId(source.getUser().getId());
        destination.setImages(source.getImages());
        destination.setBrand(brandService.findDtoById(source.getBrand().getId()).getBrandName());
        destination.setModel(modelService.findDtoById(source.getModel().getId()).getModelName());
        destination.setGeneration(generationService.findDtoById(source.getGeneration().getId()).getGenerationName());
    }

    @Override
    void mapSpecificFields(CarDto source, Car destination) {
        if (destination.getGeneration() == null ||
                !source.getGenerationId().equals(destination.getGeneration().getId())) {
            Generation generation = generationService.findWithBrandAndModel(source.getGenerationId());
            destination.setBrand(generation.getModel().getBrand());
            destination.setModel(generation.getModel());
            destination.setGeneration(generation);
        }
    }

}

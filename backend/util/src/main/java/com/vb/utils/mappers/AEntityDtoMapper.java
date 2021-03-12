package com.vb.utils.mappers;

import com.vb.api.dto.ADto;
import com.vb.entities.AEntity;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AEntityDtoMapper<E extends AEntity, D extends ADto> implements IEntityDtoMapper<E, D> {

    @Autowired
    private ModelMapper modelMapper;

    private final Class<E> entityClass;
    private final Class<D> dtoClass;

    AEntityDtoMapper(Class<E> entityClass, Class<D> dtoClass) {
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
    }


    @Override
    public D toDto(E entity) {
        return entity == null ? null : modelMapper.map(entity, dtoClass);
    }

    @Override
    public E toEntity(D dto) {
        return dto == null ? null : modelMapper.map(dto, entityClass);
    }

    @Override
    public void toEntity(D dto, E entity) {
        modelMapper.map(dto, entity);
    }

    @Override
    public void toDto(E entity, D dto) {
        modelMapper.map(entity, dto);
    }

    @Override
    public List<D> toDtoList(List<E> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<E> toEntityList(List<D> dtoList) {
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    Converter<E, D> toDtoConverter() {
        return context -> {
            E source = context.getSource();
            D destination = context.getDestination();
            mapSpecificFields(source, destination);
            return destination;
        };
    }

    Converter<D, E> toEntityConverter() {
        return context -> {
            D source = context.getSource();
            E destination = context.getDestination();
            mapSpecificFields(source, destination);
            return destination;
        };
    }

    void mapSpecificFields(E source, D destination) {
    }

    void mapSpecificFields(D source, E destination) {
    }

}

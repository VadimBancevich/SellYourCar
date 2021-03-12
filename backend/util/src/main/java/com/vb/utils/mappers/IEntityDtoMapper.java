package com.vb.utils.mappers;

import com.vb.api.dto.ADto;
import com.vb.entities.AEntity;

import java.util.List;

public interface IEntityDtoMapper<E extends AEntity, D extends ADto> {

    D toDto(E entity);

    E toEntity(D dto);

    void toEntity(D dto, E entity);

    void toDto(E entity, D dto);

    List<D> toDtoList(List<E> entityList);

    List<E> toEntityList(List<D> dtoList);

}

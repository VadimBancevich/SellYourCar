package com.vb.utils.mappers;

import com.vb.api.dto.ADto;
import com.vb.api.view.AView;
import com.vb.entities.AEntity;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AEntityDtoViewMapper<E extends AEntity, D extends ADto, V extends AView>
        extends AEntityDtoMapper<E, D>
        implements IEntityViewMapper<E, V> {

    @Autowired
    private ModelMapper modelMapper;

    private final Class<V> viewClass;

    AEntityDtoViewMapper(Class<E> entityClass, Class<D> dtoClass, Class<V> viewClass) {
        super(entityClass, dtoClass);
        this.viewClass = viewClass;
    }

    @Override
    public V toView(E entity) {
        return entity == null ? null : modelMapper.map(entity, viewClass);
    }

    @Override
    public List<V> toViewList(List<E> entities) {
        return entities.stream().map(this::toView).collect(Collectors.toList());
    }

    Converter<E, V> toViewConverter() {
        return context -> {
            E source = context.getSource();
            V destination = context.getDestination();
            mapSpecificFields(source, destination);
            return destination;
        };
    }

    void mapSpecificFields(E source, V destination) {

    }

}

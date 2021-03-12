package com.vb.utils.mappers;

import java.util.List;

public interface IEntityViewMapper<E, V> {

    V toView(E entity);

    List<V> toViewList(List<E> entities);

}

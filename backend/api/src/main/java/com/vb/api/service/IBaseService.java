package com.vb.api.service;

import com.vb.api.dto.ADto;
import com.vb.entities.AEntity;

public interface IBaseService<E extends AEntity, D extends ADto> {

     E findById(Long id);

     D findDtoById(Long id);

}

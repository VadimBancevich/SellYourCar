package com.vb.api.dao;

import com.vb.api.dao.impl.LightPage;
import com.vb.entities.Car;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;

public interface CarCustomDao {

    LightPage<Car> findByParams(MultiValueMap<String, String> params, Pageable pageable);

    Long findCountByParams(MultiValueMap<String, String> params);

}

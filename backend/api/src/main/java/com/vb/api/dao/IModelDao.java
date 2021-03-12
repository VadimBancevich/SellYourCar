package com.vb.api.dao;

import com.vb.entities.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IModelDao extends JpaRepository<Model, Long> {

    Optional<Model> findByModelNameAndBrand_Id(String modelName, Long brandId);

    List<Model> findModelsByBrand_Id(Long brandId);

}

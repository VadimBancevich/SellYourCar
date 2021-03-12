package com.vb.api.dao;

import com.vb.entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IBrandDao extends JpaRepository<Brand, Long> {

    Optional<Brand> findByBrandName(String brandName);

}

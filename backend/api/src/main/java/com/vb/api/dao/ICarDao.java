package com.vb.api.dao;

import com.vb.entities.Car;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICarDao extends JpaRepository<Car, Long>, CarCustomDao {

    @Query("select c from Car c where c.id = :carId and c.user.email = :email")
    Optional<Car> findPrincipalCarById(Long carId, String email);

    @Query("select c from Car c where c.status = 'NOT_ACTIVE'")
    List<Car> findNotActiveCars(Pageable pageable);

    List<Car> findByUser_EmailOrderByUppingDateDesc(String email);

}

package com.vb.api.dao;

import com.vb.entities.Generation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IGenerationDao extends JpaRepository<Generation, Long> {

    boolean existsByGenerationNameAndModel_Id(String generationName, Long modelId);

    List<Generation> findGenerationsByModel_Id(Long mdoelId);

    @Query("select g from Generation g join fetch g.model m join fetch m.brand b where g.id = :id")
    Optional<Generation> findWithBrandAndModelById(Long id);

}

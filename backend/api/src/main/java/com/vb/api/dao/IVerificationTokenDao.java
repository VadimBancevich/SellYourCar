package com.vb.api.dao;

import com.vb.entities.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IVerificationTokenDao extends JpaRepository<VerificationToken, Long> {

    Optional<VerificationToken> findByTokenValue(String tokenValue);

}

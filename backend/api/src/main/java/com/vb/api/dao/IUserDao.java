package com.vb.api.dao;

import com.vb.api.projections.UserIdAndName;
import com.vb.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserDao extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);

    @Query("select u from User u left join fetch u.roles where email = :email")
    Optional<User> findUserByEmailWithRoles(String email);

    boolean existsUserByEmail(String email);

    @Query("select u.id from User u where u.email = :email")
    Optional<Long> findUserIdByEmail(String email);

    Optional<User> findByCars_Id(Long carId);

    List<UserIdAndName> findIdAndNameByIdIn(Iterable<Long> ids);

}

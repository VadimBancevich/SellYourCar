package com.vb.api.dao;

import com.vb.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleDao extends JpaRepository<Role, Long> {

    Role findByRoleName(String roleName);

}

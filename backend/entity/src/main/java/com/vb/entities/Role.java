package com.vb.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "role")
public class Role extends AEntity {

    @Column(name = "role_name")
    private String roleName;

}

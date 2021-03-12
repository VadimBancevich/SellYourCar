package com.vb.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "user")
public class User extends AEntity {

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Column(name = "email", length = 45)
    private String email;

    @Column(name = "password", length = 20)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Car> cars;

    @ManyToMany
    @JoinTable(name = "role_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToMany(mappedBy = "user")
    private List<ChatUser> chatUser;

}

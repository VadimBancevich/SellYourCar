package com.vb.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "verification")
public class VerificationToken extends AEntity {

    @Column(name = "token_value")
    private String tokenValue;

    @Column(name = "expiryDate")
    private LocalDateTime expiryDate;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}

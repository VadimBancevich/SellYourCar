package com.vb.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "generation")
public class Generation extends AEntity {

    @Column(name = "generation_name", length = 45)
    private String generationName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id", referencedColumnName = "id")
    private Model model;

}

package com.vb.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "brand")
public class Brand extends AEntity {

    @Column(name = "brand_name", length = 45)
    private String brandName;

    @OneToMany(mappedBy = "brand")
    private List<Model> models;

}

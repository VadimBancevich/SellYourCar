package com.vb.api.dao;

import com.vb.entities.Brand;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

@DataJpaTest
class IBrandDaoTest {

    @Autowired
    private IBrandDao brandDao;

    @Autowired
    private TestEntityManager em;

    @Autowired
    private RandomEntityFactory ref;

    @Test
    void findByBrandNameTest() {
        Brand brand = ref.createRandomEntity(Brand.class);

        Optional<Brand> brandInDb = brandDao.findByBrandName(brand.getBrandName());
        Assert.assertTrue(brandInDb.isPresent());
        Assert.assertEquals(brand.getBrandName(), brandInDb.get().getBrandName());
    }

}
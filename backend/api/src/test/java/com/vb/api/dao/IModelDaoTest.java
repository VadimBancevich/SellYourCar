package com.vb.api.dao;

import com.vb.entities.Brand;
import com.vb.entities.Model;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@DataJpaTest
class IModelDaoTest {

    @Autowired
    private IModelDao modelDao;

    @Autowired
    private TestEntityManager em;

    @Autowired
    private RandomEntityFactory ref;

    @Test
    void findByModelNameAndBrand_IdTest() {
        Model model = ref.createRandomEntity(Model.class);
        Brand brand = model.getBrand();

        Optional<Model> modelInDb = modelDao.findByModelNameAndBrand_Id(model.getModelName(), brand.getId());
        assertTrue(modelInDb.isPresent());
        assertEquals(modelInDb.get().getModelName(), model.getModelName());
    }

    @Test
    void findModelsByBrand_IdTest() {
        List<Model> models = ref.createRandomEntity(Model.class, 10);

        List<Model> testModels = modelDao.findModelsByBrand_Id(models.get(0).getBrand().getId());
        assertEquals(10, testModels.size());
    }

}
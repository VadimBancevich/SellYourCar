package com.vb.services;

import com.vb.api.dao.IModelDao;
import com.vb.api.dto.ModelDto;
import com.vb.api.service.IModelService;
import com.vb.entities.Model;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.vb.ApplicationTest.existEntityId;
import static com.vb.ApplicationTest.notExistEntityId;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ModelServiceTest {

    @MockBean
    private IModelDao modelDao;

    @Autowired
    private IModelService modelService;

    @Test
    void getAllModelByBrandIdTest() {
        when(modelDao.findModelsByBrand_Id(existEntityId))
                .thenReturn(Stream.generate(Model::new).limit(5).collect(Collectors.toList()));

        List<ModelDto> models = modelService.getAllModelByBrandId(existEntityId);

        assertEquals(5, models.size());
    }

    @Test
    void findDtoByIdTest() {
        when(modelDao.findById(existEntityId)).thenReturn(Optional.of(new Model()));
        when(modelDao.findById(notExistEntityId)).thenReturn(Optional.empty());

        ModelDto model = modelService.findDtoById(existEntityId);

        assertNotNull(model);
        assertThrows(EntityNotFoundException.class, () -> modelService.findDtoById(notExistEntityId));
    }

}
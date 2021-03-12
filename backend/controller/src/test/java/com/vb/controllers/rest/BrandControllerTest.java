package com.vb.controllers.rest;

import com.vb.api.dto.BrandDto;
import com.vb.api.service.IBrandService;
import com.vb.controllers.MockSecurityBeansTestConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = BrandController.class)
@ContextConfiguration(classes = MockSecurityBeansTestConfiguration.class)
class BrandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IBrandService brandService;

    @Value("${api.v1.url}")
    private String apiV1;

    @Test
    void getAllBrandsTest() throws Exception {
        when(brandService.getAllBrands()).thenReturn(new ArrayList<>());

        mockMvc.perform(get(apiV1 + "/brands/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(is("[]")));
    }

    @Test
    void getBrandByIdTest() throws Exception {
        BrandDto brandDto = new BrandDto();
        brandDto.setBrandName("name");
        brandDto.setId(1L);
        when(brandService.findDtoById(1L)).thenReturn(brandDto);

        mockMvc.perform(get(apiV1 + "/brands/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("brandName", is("name")));
    }

}
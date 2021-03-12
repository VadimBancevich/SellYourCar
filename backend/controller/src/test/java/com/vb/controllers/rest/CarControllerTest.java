package com.vb.controllers.rest;

import com.vb.api.service.ICarService;
import com.vb.api.view.CarView;
import com.vb.controllers.MockSecurityBeansTestConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;
import java.util.List;

import static com.vb.ApplicationTest.existEntityId;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = MockSecurityBeansTestConfiguration.class)
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICarService carService;

    @Value("${api.v1.url}")
    private String apiV1;

    @Test
    void getMyCarsTest() throws Exception {
        CarView carView = new CarView();
        carView.setBrand("brandName1");
        carView.setUserId(existEntityId);

        when(carService.findPrincipalCars()).thenReturn(Collections.singletonList(carView));
        MvcResult mvcResult = mockMvc.perform(get(apiV1 + "/cars/my/1"))
                .andExpect(status().isOk()).andReturn();
        List<CarView> principalCars = verify(carService).findPrincipalCars();

        System.out.println("DATA - " + mvcResult.getResponse().getContentAsString());
    }

    @Test
    void getMyCarByIdTest() {
        when(carService.findPrincipalCarDto(1L));
    }

    @Test
    void createCar() {
    }

    @Test
    void updateCar() {
    }

    @Test
    void addCarImage() {
    }

    @Test
    void deleteCarImage() {
    }

    @Test
    void getByParams() {
    }

    @Test
    void getCarById() {
    }

}
package com.vb.api.dao;

import com.vb.api.dao.impl.LightPage;
import com.vb.entities.Car;
import com.vb.entities.User;
import com.vb.entities.enums.CarStatus;
import com.vb.entities.enums.Drivetrain;
import com.vb.entities.enums.Engine;
import com.vb.entities.enums.Transmission;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@DataJpaTest
class ICarDaoTest {

    @Autowired
    private ICarDao carDao;

    @Autowired
    private TestEntityManager em;

    @Autowired
    private RandomEntityFactory ref;

    @Test
    void findPrincipalCarByIdTest() {
        Car car = ref.createRandomEntity(Car.class);
        Optional<Car> principalCar = carDao.findPrincipalCarById(car.getId(), car.getUser().getEmail());

        assertTrue(principalCar.isPresent());
        assertEquals(principalCar.get().getId(), car.getId());
    }

    @Test
    void findNotActiveCarsTest() {
        List<Car> cars = ref.createRandomEntity(Car.class, 3);
        cars.stream().limit(2).forEach(car -> {
            car.setStatus(CarStatus.NOT_ACTIVE);
            em.persist(car);
        });
        em.flush();

        List<Car> notActiveCars = carDao.findNotActiveCars(PageRequest.of(0, 10));
        assertEquals(2, notActiveCars.size());
    }

    @Test
    void findByUser_EmailOrderByUppingDateDescTest() {
        List<Car> cars = ref.createRandomEntity(Car.class, 10);
        User user = ref.createRandomEntity(User.class);
        String userEmail = "email";
        user.setEmail(userEmail);
        for (int i = 0; i < 4; i++) {
            Car car = cars.get(i);
            car.setUser(user);
            car.setUppingDate(LocalDate.now().plusDays(i + 1));
        }
        em.flush();
        List<Car> testCars = carDao.findByUser_EmailOrderByUppingDateDesc(userEmail);
        assertEquals(4, testCars.size());
        boolean isOrderedByUppingDateDesc = true;
        for (int i = 0; i < testCars.size() - 1; i++) {
            if (!testCars.get(i).getUppingDate().isAfter(testCars.get(i + 1).getUppingDate())) {
                isOrderedByUppingDateDesc = false;
            }
        }
        assertTrue(isOrderedByUppingDateDesc);

    }

    @Test
    void findByParamsTest() {
        List<Car> cars = ref.createRandomEntity(Car.class, 10);
        cars.stream().limit(4).forEach(car -> {
            car.setEngine(Engine.DIESEL);
            car.setYear(2000);
            car.setDrivetrain(Drivetrain.AWD);
            car.setTransmission(Transmission.AUTO);
        });
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.put(Engine.class.getName().toLowerCase(), Collections.singletonList(Engine.DIESEL.name()));
        params.put(Drivetrain.class.getName().toLowerCase(), Collections.singletonList(Drivetrain.AWD.name()));
        params.put(Transmission.class.getName().toLowerCase(), Collections.singletonList(Transmission.AUTO.name()));
        params.put("yearFrom", Collections.singletonList("2000"));
        params.put("yearTo", Collections.singletonList("2000"));
        LightPage<Car> foundedCars = carDao.findByParams(params, PageRequest.of(0, 10));

        assertEquals(4, foundedCars.getContent().size());
    }

    @Test
    void findCountByParamsTest() {
        findByParamsTest();
    }

}
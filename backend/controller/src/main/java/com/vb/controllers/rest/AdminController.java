package com.vb.controllers.rest;

import com.vb.api.service.ICarService;
import com.vb.api.view.CarView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.v1.url}/admin")
public class AdminController {

    @Autowired
    private ICarService carService;

    @GetMapping
    public List<CarView> getNotActiveCars() {
        return carService.findNotActiveCars();
    }

    @PostMapping("/{id}")
    public void activateCar(@PathVariable Long id) {
        carService.activateCar(id);
    }

}

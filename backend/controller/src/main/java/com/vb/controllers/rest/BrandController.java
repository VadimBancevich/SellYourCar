package com.vb.controllers.rest;

import com.vb.api.dto.BrandDto;
import com.vb.api.service.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.v1.url}/brands")
public class BrandController {

    @Autowired
    private IBrandService brandService;

    @GetMapping("/all")
    public List<BrandDto> getAllBrands() {
        return brandService.getAllBrands();
    }

    @GetMapping("/{id}")
    public BrandDto getBrandById(@PathVariable Long id) {
        return brandService.findDtoById(id);
    }

}

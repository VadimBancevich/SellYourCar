package com.vb.controllers.rest;

import com.vb.api.dto.ModelDto;
import com.vb.api.service.IModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.v1.url}/models")
public class ModelController {

    @Autowired
    private IModelService modelService;

    @GetMapping
    public List<ModelDto> getAllModelsByBrandId(@RequestParam Long brandId) {
        return modelService.getAllModelByBrandId(brandId);
    }

    @GetMapping("/{id}")
    public ModelDto getModelById(@PathVariable Long id) {
        return modelService.findDtoById(id);
    }

}

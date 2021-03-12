package com.vb.controllers.rest;

import com.vb.api.dto.GenerationDto;
import com.vb.api.service.IGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.v1.url}/generations")
public class GenerationController {

    @Autowired
    private IGenerationService generationService;

    @GetMapping
    public List<GenerationDto> getAllGenerationsByModelId(@RequestParam Long modelId) {
        return generationService.getAllGenerationsByModelId(modelId);
    }

    @GetMapping("/{id}")
    public GenerationDto getGenerationById(@PathVariable Long id) {
        return generationService.findDtoById(id);
    }

}

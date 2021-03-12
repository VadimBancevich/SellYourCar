package com.vb.controllers.rest;

import com.vb.api.dao.impl.LightPage;
import com.vb.api.dto.CarDto;
import com.vb.api.service.ICarService;
import com.vb.api.view.CarView;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("${api.v1.url}/cars")
public class CarController {

    @Autowired
    private ICarService carService;

    @GetMapping(value = "/my", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CarView> getMyCars() {
        return carService.findPrincipalCars();
    }

    @GetMapping("/my/{id}")
    public CarDto getMyCarById(@PathVariable Long id) {
        return carService.findPrincipalCarDto(id);
    }

    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    public void createCar(@RequestPart CarDto carDto,
                          @RequestPart(required = false) List<MultipartFile> imagesFiles) {
        carService.saveCar(carDto, imagesFiles);
    }

    @PutMapping
    public CarView updateCar(@RequestBody CarDto carDto) {
        return carService.updateCar(carDto);
    }

    @PostMapping(value = "/images/add", consumes = MULTIPART_FORM_DATA_VALUE)
    public void addCarImage(@RequestPart @Parameter(required = true) Long carId,
                            @RequestPart @Parameter(required = true) MultipartFile image) {
        carService.addImage(carId, image);
    }

    @DeleteMapping("/images")
    public void deleteCarImage(@RequestParam Long carId, @RequestParam String imageUrl) {
        carService.deleteImage(carId, imageUrl);
    }

    @Parameters(value = {
            @Parameter(name = "brandId", in = QUERY, schema = @Schema(type = "integer")),
            @Parameter(name = "modelId", in = QUERY, schema = @Schema(type = "integer")),
            @Parameter(name = "generationId", in = QUERY, schema = @Schema(type = "integer")),
            @Parameter(name = "bodyType", in = QUERY, array = @ArraySchema(schema = @Schema(
                    allowableValues = {"CONVERTIBLE", "COUPE", "HATCHBACK", "LIMOUSINE", "MINIVAN", "PICKUP",
                            "ROADSTER", "SEDAN", "SUV", "VAN", "WAGON"}))),
            @Parameter(name = "engine", in = QUERY, array = @ArraySchema(schema = @Schema(
                    allowableValues = {"GASOLINE", "DIESEL", "HYBRID", "ELECTRIC"}))),
            @Parameter(name = "engineVolumeFrom", in = QUERY, description = "engine volume in cubic centimeters",
                    schema = @Schema(type = "integer", minimum = "1", maximum = "10000")),
            @Parameter(name = "engineVolumeTo", in = QUERY, description = "engine volume in cubic centimeters",
                    schema = @Schema(type = "integer", minimum = "1", maximum = "10000")),
            @Parameter(name = "yearFrom", in = QUERY, schema = @Schema(type = "integer")),
            @Parameter(name = "yearTo", in = QUERY, schema = @Schema(type = "integer")),
            @Parameter(name = "condition", in = QUERY, array = @ArraySchema(schema = @Schema(
                    allowableValues = {"NEW", "USED", "CRASH"}))),
            @Parameter(name = "mileageFrom", in = QUERY, schema = @Schema(type = "integer")),
            @Parameter(name = "mileageTo", in = QUERY, schema = @Schema(type = "integer")),
            @Parameter(name = "transmission", array = @ArraySchema(schema = @Schema(allowableValues = {"AUTO", "MANUAL"}))),
            @Parameter(name = "drivetrain", in = QUERY, array = @ArraySchema(schema = @Schema(allowableValues = {"FWD", "RWD"}))),
            @Parameter(name = "gas", in = QUERY, array = @ArraySchema(schema = @Schema(allowableValues = {"PROPANE", "METHANE"}))),
            @Parameter(name = "sortBy", in = QUERY, schema = @Schema(allowableValues = {"price, year"})),
            @Parameter(name = "sortDir", in = QUERY, schema = @Schema(defaultValue = "asc", allowableValues = {"asc", "desc"}))
    })
    @GetMapping(value = "/find", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public LightPage<CarView> getByParams(@RequestParam @Parameter(hidden = true) MultiValueMap<String, String> params,
                                          @RequestParam(defaultValue = "1") Integer page,
                                          @RequestParam(defaultValue = "20") Integer size) {
        return carService.findViewsByParams(params, page, size);

    }

    @GetMapping(value = "/{id}")
    public CarView getCarById(@PathVariable Long id) {
        return carService.findViewById(id);
    }

}

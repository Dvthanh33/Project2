package com.javaweb.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.model.BuildingDTO;
import com.javaweb.service.BuildingService;

@RestController
public class BuildingAPI {

    @Autowired
    private BuildingService buildingService;

    @GetMapping("/api/building")
    public List<BuildingDTO> getBuilding(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String district,
            @RequestParam(required = false) String ward,
            @RequestParam(required = false) String street,
            @RequestParam(required = false) Integer numberOfBasement,
            @RequestParam(required = false) String direction,
            @RequestParam(required = false) String level,
            @RequestParam(required = false) Integer areaFrom,
            @RequestParam(required = false) Integer areaTo,
            @RequestParam(required = false) Integer rentPriceFrom,
            @RequestParam(required = false) Integer rentPriceTo,
            @RequestParam(required = false) String managerName,
            @RequestParam(required = false) String managerPhone,
            @RequestParam(required = false) Long staffId,
            @RequestParam(required = false) String furniture
    ) {

        return buildingService.findAll(
                name, district, ward, street,
                numberOfBasement, direction, level,
                areaFrom, areaTo,
                rentPriceFrom, rentPriceTo,
                managerName, managerPhone,
                staffId, furniture
        );
    }
}

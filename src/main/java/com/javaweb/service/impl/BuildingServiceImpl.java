package com.javaweb.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaweb.model.BuildingDTO;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.service.BuildingService;

@Service
public class BuildingServiceImpl implements BuildingService {

    @Autowired
    private BuildingRepository buildingRepository;

    @Override
    public List<BuildingDTO> findAll(
            String name,
            String district,
            String ward,
            String street,
            Integer numberOfBasement,
            String direction,
            String level,
            Integer areaFrom,
            Integer areaTo,
            Integer rentPriceFrom,
            Integer rentPriceTo,
            String managerName,
            String managerPhone,
            Long staffId,
            String furniture
    ) {

        List<BuildingEntity> buildingEntities =
                buildingRepository.findAll(
                        name, district, ward, street,
                        numberOfBasement, direction, level,
                        areaFrom, areaTo,
                        rentPriceFrom, rentPriceTo,
                        managerName, managerPhone,
                        staffId, furniture
                );

        List<BuildingDTO> result = new ArrayList<>();

        for (BuildingEntity entity : buildingEntities) {
            result.add(convertToDTO(entity));
        }

        return result;
    }

    // Mapping Entity -> DTO
    private BuildingDTO convertToDTO(BuildingEntity entity) {
        BuildingDTO dto = new BuildingDTO();

        dto.setName(entity.getName());
        dto.setNumberOfBasement(entity.getNumberOfBasement());
        dto.setRentPrice(entity.getRentPrice());
        dto.setManagerName(entity.getManagerName());
        dto.setManagerPhone(entity.getManagerPhone());
        dto.setFloorArea(entity.getFloorArea());

        // GHÉP ADDRESS (QUAN TRỌNG)
        String address = "";
        if (entity.getStreet() != null) {
            address += entity.getStreet() + ", ";
        }
        if (entity.getWard() != null) {
            address += entity.getWard() + ", ";
        }
        if (entity.getDistrict() != null) {
            address += entity.getDistrict();
        }

        dto.setAddress(address);

        return dto;
    }
}



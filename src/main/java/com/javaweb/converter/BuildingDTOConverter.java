package com.javaweb.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.javaweb.model.BuildingDTO;
import com.javaweb.repository.DistrictRepository;
import com.javaweb.repository.RentAreaRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.DistrictEntity;
import com.javaweb.repository.entity.RentAreaEntity;

@Component
public class BuildingDTOConverter {
	@Autowired
    private DistrictRepository districtRepository;
    @Autowired
    private RentAreaRepository rentareaRepository;
    @Autowired
    private ModelMapper modelMapper;
    public BuildingDTO toBuildingDTO(BuildingEntity entity) {
    	BuildingDTO building = modelMapper.map(entity, BuildingDTO.class); // thay cho viec set,get tung key
        DistrictEntity districtEntity = districtRepository.findNameById(entity.getDistrictId());
        building.setAddress(entity.getStreet() + "," + entity.getWard() + "," + districtEntity.getName());
        List<RentAreaEntity> rentAreas = rentareaRepository.getValueByBuildingId(entity.getId());
        String areaResult = rentAreas.stream().map(item->item.getValue().toString()).collect(Collectors.joining(","));
//        building.setNumberOfBasement(entity.getNumberOfBasement());
//        building.setName(entity.getName());
//        building.setManagerName(entity.getManagerName());
//        building.setManagerPhone(entity.getManagerPhone());
//        building.setFloorArea(entity.getFloorArea());
//        building.setRentPrice(entity.getRentPrice());
//        building.setServiceFee(entity.getBrokerageFee());
//        building.setBrokerageFee(entity.getBrokerageFee());
        building.setRentarea(areaResult);
		return building;
    }
}
//dung de chuyen doi dữ liệu
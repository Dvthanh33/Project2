package com.javaweb.converter;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.javaweb.Utils.MapUtil;
import com.javaweb.builder.BuildingSearchBuilder;

@Component
public class BuildingSearchBuilderConverter {

    public BuildingSearchBuilder toBuildingSearchBuilder(Map<String, Object> params, List<String> typeCode) {
        return new BuildingSearchBuilder.Builder()
                .setName(MapUtil.getObject(params, "name", String.class))
                .setFloorArea(MapUtil.getObject(params, "floorArea", Integer.class))
                .setNumberOfBasement(MapUtil.getObject(params, "numberOfBasement", Integer.class))
                .setDistrictId(MapUtil.getObject(params, "districtid", Long.class))
                .setWard(MapUtil.getObject(params, "ward", String.class))
                .setStreet(MapUtil.getObject(params, "street", String.class))
                .setManagerName(MapUtil.getObject(params, "managerName", String.class))
                .setManagerPhoneNumber(MapUtil.getObject(params, "managerPhone", String.class))
                .setRentPriceFrom(MapUtil.getObject(params, "rentpricefrom", Long.class))
                .setRentPriceTo(MapUtil.getObject(params, "rentpriceto", Long.class))
                .setAreaFrom(MapUtil.getObject(params, "areaFrom", Long.class))
                .setAreaTo(MapUtil.getObject(params, "areaTo", Long.class))
                .setTypeCode(typeCode)
                .build();
    }
}

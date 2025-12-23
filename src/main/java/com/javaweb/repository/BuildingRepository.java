package com.javaweb.repository;

import java.util.List;


import com.javaweb.repository.entity.BuildingEntity;

public interface BuildingRepository {


	List<BuildingEntity> findAll(String name, String district, String ward, String street, Integer numberOfBasement,
			String direction, String level, Integer areaFrom, Integer areaTo, Integer rentPriceFrom,
			Integer rentPriceTo, String managerName, String managerPhone, Long staffId, String furniture);
}

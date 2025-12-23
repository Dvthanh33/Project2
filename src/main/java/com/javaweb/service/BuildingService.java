package com.javaweb.service;
import java.util.List;

import com.javaweb.model.BuildingDTO;


public interface BuildingService {

	List<BuildingDTO> findAll(String name, String district, String ward, String street, Integer numberOfBasement,
			String direction, String level, Integer areaFrom, Integer areaTo, Integer rentPriceFrom,
			Integer rentPriceTo, String managerName, String managerPhone, Long staffId, String furniture);
}

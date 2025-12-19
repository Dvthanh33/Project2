package com.javaweb.api;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Connection;
import java.util.List;
import java.sql.Statement;
import java.sql.ResultSet;

import org.springframework.web.bind.annotation.RestController;

import com.javaweb.model.BuildingDTO;
import com.javaweb.service.BuildingService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class BuildingAPI {
	@Autowired
	private BuildingService buildingService;
	
		@GetMapping(value="/api/building/")
			public List<BuildingDTO> getBuilding(@RequestParam(name="name", required = false)String name,
												@RequestParam(name="district_id",required = false)Long district,
												@RequestParam(name="typeCode",required = false) List<String>typeCode
					){
			List<BuildingDTO> result = buildingService.findAll(name,district);
			return result;
	}
}

package com.javaweb.api;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


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
            HttpServletRequest request,
            @RequestParam(required = false) List<String> typeCode) {
        Map<String, Object> params = new HashMap<>();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String key = paramNames.nextElement();
            if (!"typeCode".equals(key)) {
                params.put(key, request.getParameter(key));
            }
        }
        return buildingService.findAll(params, typeCode);
    }
}

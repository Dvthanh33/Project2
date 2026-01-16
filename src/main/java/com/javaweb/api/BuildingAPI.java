package com.javaweb.api;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.model.BuildingDTO;
import com.javaweb.model.BuildingRequestDTO;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.DistrictEntity;
import com.javaweb.service.BuildingService;

@RestController
@Transactional
public class BuildingAPI {
    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private BuildingService buildingService;
    @PersistenceContext
	private EntityManager entityManager;
    @GetMapping(value="/api/building")
    public List<BuildingDTO> getBuilding(
            @RequestParam Map<String, Object> params,
            @RequestParam(required = false) List<String> typeCode) {
        List<BuildingDTO> result = buildingService.findAll(params, typeCode);
        return result;
    }
    @PostMapping("/api/building")
    public void createBuilding(@RequestBody BuildingRequestDTO buildingRequestDTO) {
        BuildingEntity builEntity = new BuildingEntity();
        builEntity.setName(buildingRequestDTO.getName());
        builEntity.setStreet(buildingRequestDTO.getStreet());
        builEntity.setWard(buildingRequestDTO.getWard());
        // Lấy reference entity đã tồn tại
        DistrictEntity district = entityManager.getReference(
                DistrictEntity.class,
                buildingRequestDTO.getDistrictId());
        builEntity.setDistrict(district);
        entityManager.persist(builEntity);
    }
    @GetMapping(value="/api/building/{id}")
    public BuildingDTO getBuildingById(@PathVariable Long id) {
    	BuildingDTO result = new BuildingDTO();
        BuildingEntity building = buildingRepository.findById(id).get();
        return result;
    }
    @PutMapping("/api/building")
    public void updateBuilding(@RequestBody BuildingRequestDTO buildingRequestDTO) {
        BuildingEntity builEntity = new BuildingEntity();
        builEntity.setId(buildingRequestDTO.getId());
        builEntity.setName(buildingRequestDTO.getName());
        builEntity.setStreet(buildingRequestDTO.getStreet());
        builEntity.setWard(buildingRequestDTO.getWard());
        DistrictEntity district = entityManager.getReference(DistrictEntity.class,buildingRequestDTO.getDistrictId());
        builEntity.setDistrict(district);
        buildingRepository.save(builEntity);
        //entityManager.merge(builEntity);
    }
    @DeleteMapping("/api/building/{ids}")
    public void deleteBuilding(@PathVariable List<Long> ids) {
    	//JPA
//    	BuildingEntity buildingEntity = entityManager.find(BuildingEntity.class, id);
//    	entityManager.remove(buildingEntity);
    	
    	//Spring Data JPA
    	//buildingRepository.deleteById(id);
    	buildingRepository.deleteByIdIn(ids);
    }
}


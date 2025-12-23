package com.javaweb.repository.impl;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/estateadvance";
    private static final String USER = "root";
    private static final String PASS = "";
    @Override
    public List<BuildingEntity> findAll(
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

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DISTINCT b.* ");
        sql.append("FROM building b ");
        sql.append("LEFT JOIN rentarea r ON b.id = r.buildingid ");
        sql.append("LEFT JOIN assignmentbuilding ab ON b.id = ab.buildingid ");
        sql.append("WHERE 1=1 ");

        List<Object> params = new ArrayList<>();

        if (name != null && !name.isBlank()) {
            sql.append("AND b.name LIKE ? ");
            params.add("%" + name + "%");
        }

        if (district != null && !district.isBlank()) {
            sql.append("AND b.district = ? ");
            params.add(district);
        }

        if (ward != null && !ward.isBlank()) {
            sql.append("AND b.ward LIKE ? ");
            params.add("%" + ward + "%");
        }

        if (street != null && !street.isBlank()) {
            sql.append("AND b.street LIKE ? ");
            params.add("%" + street + "%");
        }

        if (numberOfBasement != null) {
            sql.append("AND b.numberofbasement = ? ");
            params.add(numberOfBasement);
        }

        if (direction != null && !direction.isBlank()) {
            sql.append("AND b.direction = ? ");
            params.add(direction);
        }

        if (level != null && !level.isBlank()) {
            sql.append("AND b.level = ? ");
            params.add(level);
        }

        if (areaFrom != null) {
            sql.append("AND r.value >= ? ");
            params.add(areaFrom);
        }

        if (areaTo != null) {
            sql.append("AND r.value <= ? ");
            params.add(areaTo);
        }

        if (rentPriceFrom != null) {
            sql.append("AND b.rentprice >= ? ");
            params.add(rentPriceFrom);
        }

        if (rentPriceTo != null) {
            sql.append("AND b.rentprice <= ? ");
            params.add(rentPriceTo);
        }

        if (managerName != null && !managerName.isBlank()) {
            sql.append("AND b.managername LIKE ? ");
            params.add("%" + managerName + "%");
        }

        if (managerPhone != null && !managerPhone.isBlank()) {
            sql.append("AND b.managerphone LIKE ? ");
            params.add("%" + managerPhone + "%");
        }

        if (staffId != null) {
            sql.append("AND ab.staffid = ? ");
            params.add(staffId);
        }

        if (furniture != null && !furniture.isBlank()) {
            sql.append("AND b.furniture LIKE ? ");
            params.add("%" + furniture + "%");
        }

        List<BuildingEntity> result = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                BuildingEntity building = new BuildingEntity();
                building.setName(rs.getString("name"));
                building.setNumberOfBasement(rs.getInt("numberofbasement"));
                building.setStreet(rs.getString("street"));
                building.setWard(rs.getString("ward"));
                building.setDistrict(rs.getString("district"));
                building.setRentPrice(rs.getInt("rentprice"));
                building.setManagerName(rs.getString("managername"));
                building.setFloorArea(rs.getInt("floorarea"));
                building.setManagerPhone(rs.getString("managerphone"));
                result.add(building);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}

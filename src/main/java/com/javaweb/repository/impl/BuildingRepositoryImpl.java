package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.javaweb.Utils.ConnectionJDBCUtil;
import com.javaweb.Utils.StringUtil;
import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository {
    private void joinTable(BuildingSearchBuilder builder, StringBuilder sql) {
        if (builder.getTypeCode() != null && !builder.getTypeCode().isEmpty()) {
            sql.append(" INNER JOIN buildingrenttype brt ON b.id = brt.buildingid ");
            sql.append(" INNER JOIN renttype rt ON rt.id = brt.renttypeid ");
        }
        if (builder.getAreaFrom() != null || builder.getAreaTo() != null) {
            sql.append(" INNER JOIN rentarea ra ON b.id = ra.buildingid ");
        }
    }
    private void queryNormal(BuildingSearchBuilder builder, StringBuilder where) {
        if (StringUtil.checkString(builder.getName())) {
            where.append(" AND b.name LIKE '%").append(builder.getName()).append("%'");
        }
        if (builder.getDistrictId() != null) {
            where.append(" AND b.districtid = ").append(builder.getDistrictId());
        }
    }
    private void querySpecial(BuildingSearchBuilder builder, StringBuilder where) {
        if (builder.getAreaFrom() != null) {
            where.append(" AND ra.value >= ").append(builder.getAreaFrom());
        }
        if (builder.getAreaTo() != null) {
            where.append(" AND ra.value <= ").append(builder.getAreaTo());
        }
        if (builder.getRentPriceFrom() != null) {
            where.append(" AND b.rentprice >= ").append(builder.getRentPriceFrom());
        }
        if (builder.getTypeCode() != null && !builder.getTypeCode().isEmpty()) {
            where.append(" AND rt.code IN (");
            String codes = builder.getTypeCode().stream()
                    .map(code -> "'" + code + "'")
                    .collect(Collectors.joining(","));
            where.append(codes).append(")");
        }
    }
    @Override
    public List<BuildingEntity> findAll(BuildingSearchBuilder builder) {
        StringBuilder sql = new StringBuilder(
                "SELECT DISTINCT b.* FROM building b ");
        joinTable(builder, sql);
        StringBuilder where = new StringBuilder(" WHERE 1=1 ");
        queryNormal(builder, where);
        querySpecial(builder, where);

        sql.append(where);
        System.out.println("SQL = " + sql);
        List<BuildingEntity> result = new ArrayList<>();
        try (Connection conn = ConnectionJDBCUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql.toString())) {
            while (rs.next()) {
                BuildingEntity b = new BuildingEntity();
                b.setId(rs.getLong("id"));
                b.setName(rs.getString("name"));
                b.setStreet(rs.getString("street"));
                b.setWard(rs.getString("ward"));
                b.setDistrictId(rs.getLong("districtid"));
                b.setFloorArea(rs.getInt("floorarea"));
                b.setRentPrice(rs.getInt("rentprice"));
                b.setManagerName(rs.getString("managername"));
                b.setManagerPhone(rs.getString("managerphone"));
                result.add(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}

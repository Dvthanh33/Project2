package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.javaweb.Utils.NumberUtil;
import com.javaweb.Utils.StringUtil;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/estateadvance";
    private static final String USER = "root";
    private static final String PASS = "";

    /*JOIN TABLE*/
    public static void joinTable(Map<String, Object> params, List<String> typeCode, StringBuilder sql) {
        // JOIN staff
        String staffId = (String) params.get("staffId");
        if (StringUtil.checkString(staffId)) {
            sql.append(" INNER JOIN assignmentbuilding ab ON b.id = ab.buildingid ");
        }
        // JOIN rent type
        if (typeCode != null && !typeCode.isEmpty()) {
            sql.append(" INNER JOIN buildingrenttype brt ON b.id = brt.buildingid ");
            sql.append(" INNER JOIN renttype rt ON rt.id = brt.renttypeid ");
        }
    }
    /* NORMAL QUERY*/
    public static void queryNormal(Map<String, Object> params, StringBuilder where) {
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().toString();
            if (!StringUtil.checkString(value)) continue;
            // bỏ qua các field xử lý riêng
            if (key.equals("staffId")
             || key.equals("typeCode")
             || key.equals("areaFrom")
             || key.equals("areaTo")
             || key.equals("rentPriceFrom")
             || key.equals("rentPriceTo")) {
                continue;
            }
            if (NumberUtil.isNumber(value)) {
                where.append(" AND b.").append(key).append(" = ").append(value);
            } else {
                where.append(" AND b.").append(key)
                     .append(" LIKE '%").append(value).append("%' ");
            }
        }
    }
    /* SPECIAL QUERY*/
    public static void querySpecial(Map<String, Object> params, List<String> typeCode, StringBuilder where) {
        // staffId
        String staffId = (String) params.get("staffId");
        if (StringUtil.checkString(staffId)) {
            where.append(" AND ab.staffid = ").append(staffId);
        }
        // area (LỌC THEO FLOORAREA)
        String areaFrom = (String) params.get("areaFrom");
        String areaTo = (String) params.get("areaTo");

        if (StringUtil.checkString(areaFrom)) {
            where.append(" AND b.floorarea >= ").append(areaFrom);
        }
        if (StringUtil.checkString(areaTo)) {
            where.append(" AND b.floorarea <= ").append(areaTo);
        }
        // rent price
        String rentPriceFrom = (String) params.get("rentPriceFrom");
        String rentPriceTo = (String) params.get("rentPriceTo");

        if (StringUtil.checkString(rentPriceFrom)) {
            where.append(" AND b.rentprice >= ").append(rentPriceFrom);
        }
        if (StringUtil.checkString(rentPriceTo)) {
            where.append(" AND b.rentprice <= ").append(rentPriceTo);
        }
        // typeCode
        if (typeCode != null && !typeCode.isEmpty()) {
            List<String> codes = new ArrayList<>();
            for (String code : typeCode) {
                codes.add("'" + code + "'");
            }
            where.append(" AND rt.code IN (")
                 .append(String.join(",", codes))
                 .append(") ");
        }
    }
    /*FIND ALL */
    @Override
    public List<BuildingEntity> findAll(Map<String, Object> params, List<String> typeCode) {
        StringBuilder sql = new StringBuilder(
                "SELECT b.id, b.name, b.street, b.ward, b.districtid, "
              + "b.numberofbasement, b.floorarea, b.rentprice, "
              + "b.managername, b.managerphone, b.servicefee, b.brokeragefee "
              + "FROM building b ");
        joinTable(params, typeCode, sql);
        StringBuilder where = new StringBuilder(" WHERE 1=1 ");
        queryNormal(params, where);
        querySpecial(params, typeCode, where);
        where.append(" GROUP BY b.id ");
        sql.append(where);
        System.out.println("SQL = " + sql);
        List<BuildingEntity> result = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql.toString())) {
            while (rs.next()) {
                BuildingEntity building = new BuildingEntity();
                building.setName(rs.getString("name"));
                building.setStreet(rs.getString("street"));
                building.setWard(rs.getString("ward"));
                building.setDistrictId(rs.getLong("districtid"));
                building.setNumberOfBasement(rs.getInt("numberofbasement"));
                building.setFloorArea(rs.getInt("floorarea"));
                building.setRentPrice(rs.getInt("rentprice"));
                building.setManagerName(rs.getString("managername"));
                building.setManagerPhone(rs.getString("managerphone"));
                building.setServiceFee(rs.getString("servicefee"));
                building.setBrokerageFee(rs.getLong("brokeragefee"));
                result.add(building);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}

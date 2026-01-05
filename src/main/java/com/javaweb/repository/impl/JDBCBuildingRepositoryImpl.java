package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import com.javaweb.Utils.ConnectionJDBCUtil;
import com.javaweb.Utils.StringUtil;
import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;

import lombok.Value;

@Repository
public class JDBCBuildingRepositoryImpl implements BuildingRepository {
	@PersistenceContext
	private EntityManager entityManager;
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
        StringBuilder sql = new StringBuilder("SELECT DISTINCT b.* FROM building b ");
        joinTable(builder, sql);
        StringBuilder where = new StringBuilder(" WHERE 1=1 ");
        queryNormal(builder, where);
        querySpecial(builder, where);
        sql.append(where);
        System.out.println("SQL = " + sql);
       Query query = entityManager.createNativeQuery(sql.toString(), BuildingEntity.class);
        return query.getResultList();
    }
}

package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.javaweb.Utils.ConnectionJDBCUtil;
import com.javaweb.repository.DistrictRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.DistrictEntity;
@Repository
public class DistrictRepositoryImpl implements DistrictRepository {
	@Override
	public DistrictEntity findNameById(Long id) {
		String sql = "SELECT d.name FROM district d WHERE d.id = " + id +"";
		DistrictEntity districtEntity = new DistrictEntity();
		try (Connection conn = ConnectionJDBCUtil.getConnection();
	            PreparedStatement ps = conn.prepareStatement(sql)) {
	            ResultSet rs = ps.executeQuery();
	            while (rs.next()) {
	             districtEntity.setName(rs.getString("name"));
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		return districtEntity;
	}

}

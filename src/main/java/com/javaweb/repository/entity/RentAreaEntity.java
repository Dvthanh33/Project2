package com.javaweb.repository.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name = "rentarea")
	public class RentAreaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String value;
	@ManyToOne
    @JoinColumn(name = "buildingid")
    private BuildingEntity building; //mappedby
	
public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public BuildingEntity getBuilding() {
		return building;
	}
	public void setBuilding(BuildingEntity building) {
		this.building = building;
	}
public String getValue() {
	return value;
}
public void setValue(String value) {
	this.value = value;
}
}

package com.javaweb.Builder;

import java.util.ArrayList;
import java.util.List;

// design pattern Builder 
public class BuildingSearchBuilder {
	private String name;
	private Long floorArea;
	private String ward;
	private String street;
	private Long districtid;
	private Integer numberOfBasement;
	private List<String> typeCode = new ArrayList<String>();
	private String managerName;
	private String managerPhoneNumber;
	private Long rentPriceFrom;
	private Long rentPriceTo;
	private Long areaFrom;
	private Long areaTo;
	private Long staffId;
	
	private BuildingSearchBuilder(Builder build) {
		this.name = build.name;
		this.floorArea = build.floorArea;
		this.ward = build.ward;
		this.street = build.street;
		this.districtid = build.districtid;
		this.numberOfBasement = build.numberOfBasement;
		this.typeCode = build.typeCode;
		this.managerName = build.managerName;
		this.managerPhoneNumber = build.managerPhoneNumber;
		this.rentPriceFrom = build.rentPriceFrom;
		this.rentPriceTo = build.rentPriceTo;
		this.areaFrom = build.areaFrom;
		this.areaTo = build.areaTo;
		this.staffId = build.staffId;
	}
	
	public String getName() {
		return name;
	}
	public Long getFloorArea() {
		return floorArea;
	}
	public String getWard() {
		return ward;
	}
	public String getStreet() {
		return street;
	}
	public Long getDistrictid() {
		return districtid;
	}
	public Integer getNumberOfBasement() {
		return numberOfBasement;
	}
	public List<String> getTypeCode() {
		return typeCode;
	}
	public String getManagerName() {
		return managerName;
	}
	public String getManagerPhoneNumber() {
		return managerPhoneNumber;
	}
	public Long getRentPriceFrom() {
		return rentPriceFrom;
	}
	public Long getRentPriceTo() {
		return rentPriceTo;
	}
	public Long getAreaFrom() {
		return areaFrom;
	}
	public Long getAreaTo() {
		return areaTo;
	}
	public Long getStaffId() {
		return staffId;
	}
	
	public static class Builder{
		private String name;
		private Long floorArea;
		private String ward;
		private String street;
		private Long districtid;
		private Integer numberOfBasement;
		private List<String> typeCode = new ArrayList<String>();
		private String managerName;
		private String managerPhoneNumber;
		private Long rentPriceFrom;
		private Long rentPriceTo;
		private Long areaFrom;
		private Long areaTo;
		private Long staffId;
		
		public Builder setName(String name) {
			this.name = name;
			return this;
		}
		public Builder setFloorArea(Long floorArea) {
			this.floorArea = floorArea;
			return this;
		}
		public Builder setWard(String ward) {
			this.ward = ward;
			return this;
		}
		public Builder setStreet(String street) {
			this.street = street;
			return this;
		}
		public Builder setDistrictcode(Long districtid) {
			this.districtid = districtid;
			return this;
		}
		public Builder setNumberOfBasement(Integer numberOfBasement) {
			this.numberOfBasement = numberOfBasement;
			return this;
		}
		public Builder setTypeCode(List<String> typeCode) {
			this.typeCode = typeCode;
			return this;
		}
		public Builder setManagerName(String managerName) {
			this.managerName = managerName;
			return this;
		}
		public Builder setManagerPhoneNumber(String managerPhoneNumber) {
			this.managerPhoneNumber = managerPhoneNumber;
			return this;
		}
		public Builder setRentPriceFrom(Long rentPriceFrom) {
			this.rentPriceFrom = rentPriceFrom;
			return this;
		}
		public Builder setRentPriceTo(Long rentPriceTo) {
			this.rentPriceTo = rentPriceTo;
			return this;
		}
		public Builder setAreaFrom(Long areaFrom) {
			this.areaFrom = areaFrom;
			return this;
		}
		public Builder setAreaTo(Long areaTo) {
			this.areaTo = areaTo;
			return this;
		}
		public Builder setStaffId(Long staffId) {
			this.staffId = staffId;
			return this;
		}
		public BuildingSearchBuilder Build() {
			return new BuildingSearchBuilder(this);
		}
	}
}

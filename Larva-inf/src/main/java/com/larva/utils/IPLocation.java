package com.larva.utils;

public class IPLocation {
	private String areaId;
	private String country;
	private String area;

	public IPLocation() {
	}

	public IPLocation(String country, String area) {
		this.country = country;
		this.area = area;
	}

	public IPLocation getCopy() {
		return new IPLocation(this.country, this.area);
	}

	public String getArea() {
		return " CZ88.NET".equals(this.area) ? "" : this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCountry() {
		return " CZ88.NET".equals(this.country) ? "" : this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * <b>Return</b> the property areaId
	 */
	public String getAreaId() {
		return areaId;
	}

	/**
	 * <b>Param</b> areaId the areaId to set
	 */
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
}
package com.ssafy.homepjt.model.dto;

public class HouseDealDto {
	private long id;
	private String dealAmount;
	private int dealYear;
	private int dealMonth;
	private int dealDay;
	private String area;
	private String floor;
	private String cancelDealType;
	private long aptCode;

	public HouseDealDto() {
		super();
	}

	public HouseDealDto(long id, String dealAmount, int dealYear, int dealMonth, int dealDay, String area, String floor,
			String cancelDealType, long aptCode) {
		super();
		this.id = id;
		this.dealAmount = dealAmount;
		this.dealYear = dealYear;
		this.dealMonth = dealMonth;
		this.dealDay = dealDay;
		this.area = area;
		this.floor = floor;
		this.cancelDealType = cancelDealType;
		this.aptCode = aptCode;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDealAmount() {
		return dealAmount;
	}

	public void setDealAmount(String dealAmount) {
		this.dealAmount = dealAmount;
	}

	public int getDealYear() {
		return dealYear;
	}

	public void setDealYear(int dealYear) {
		this.dealYear = dealYear;
	}

	public int getDealMonth() {
		return dealMonth;
	}

	public void setDealMonth(int dealMonth) {
		this.dealMonth = dealMonth;
	}

	public int getDealDay() {
		return dealDay;
	}

	public void setDealDay(int dealDay) {
		this.dealDay = dealDay;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getCancelDealType() {
		return cancelDealType;
	}

	public void setCancelDealType(String cancelDealType) {
		this.cancelDealType = cancelDealType;
	}

	public long getAptCode() {
		return aptCode;
	}

	public void setAptCode(long aptCode) {
		this.aptCode = aptCode;
	}

	@Override
	public String toString() {
		return "HouseDealDto [id=" + id + ", dealAmount=" + dealAmount + ", dealYear=" + dealYear + ", dealMonth="
				+ dealMonth + ", dealDay=" + dealDay + ", area=" + area + ", floor=" + floor + ", cancelDealType="
				+ cancelDealType + ", aptCode=" + aptCode + "]";
	}

}

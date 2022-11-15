package com.ssafy.homepjt.model.dto;

public class HouseInfoDto {
	private long aptCode;
	private int buildYear;
	private String roadName;
	private String roadNameBonbun;
	private String roadNameBubun;
	private String roadNameSeq;
	private String roadNameBasementCode;
	private String roadNameCode;
	private String dong;
	private String bonbun;
	private String bubun;
	private String sigugunCode;
	private String eubmyungdongCode;
	private String landCode;
	private String apartmentName;
	private String jibun;
	private String lon;
	private String lat;
	private int viewCnt;
	private int likeCnt;

	public HouseInfoDto() {
		super();
	}

	public HouseInfoDto(long aptCode, int buildYear, String roadName, String roadNameBonbun, String roadNameBubun,
			String roadNameSeq, String roadNameBasementCode, String roadNameCode, String dong, String bonbun,
			String bubun, String sigugunCode, String eubmyungdongCode, String landCode, String apartmentName,
			String jibun, String lon, String lat, int viewCnt, int likeCnt) {
		super();
		this.aptCode = aptCode;
		this.buildYear = buildYear;
		this.roadName = roadName;
		this.roadNameBonbun = roadNameBonbun;
		this.roadNameBubun = roadNameBubun;
		this.roadNameSeq = roadNameSeq;
		this.roadNameBasementCode = roadNameBasementCode;
		this.roadNameCode = roadNameCode;
		this.dong = dong;
		this.bonbun = bonbun;
		this.bubun = bubun;
		this.sigugunCode = sigugunCode;
		this.eubmyungdongCode = eubmyungdongCode;
		this.landCode = landCode;
		this.apartmentName = apartmentName;
		this.jibun = jibun;
		this.lon = lon;
		this.lat = lat;
		this.viewCnt = viewCnt;
		this.likeCnt = likeCnt;
	}

	public long getAptCode() {
		return aptCode;
	}

	public void setAptCode(long aptCode) {
		this.aptCode = aptCode;
	}

	public int getBuildYear() {
		return buildYear;
	}

	public void setBuildYear(int buildYear) {
		this.buildYear = buildYear;
	}

	public String getRoadName() {
		return roadName;
	}

	public void setRoadName(String roadName) {
		this.roadName = roadName;
	}

	public String getRoadNameBonbun() {
		return roadNameBonbun;
	}

	public void setRoadNameBonbun(String roadNameBonbun) {
		this.roadNameBonbun = roadNameBonbun;
	}

	public String getRoadNameBubun() {
		return roadNameBubun;
	}

	public void setRoadNameBubun(String roadNameBubun) {
		this.roadNameBubun = roadNameBubun;
	}

	public String getRoadNameSeq() {
		return roadNameSeq;
	}

	public void setRoadNameSeq(String roadNameSeq) {
		this.roadNameSeq = roadNameSeq;
	}

	public String getRoadNameBasementCode() {
		return roadNameBasementCode;
	}

	public void setRoadNameBasementCode(String roadNameBasementCode) {
		this.roadNameBasementCode = roadNameBasementCode;
	}

	public String getRoadNameCode() {
		return roadNameCode;
	}

	public void setRoadNameCode(String roadNameCode) {
		this.roadNameCode = roadNameCode;
	}

	public String getDong() {
		return dong;
	}

	public void setDong(String dong) {
		this.dong = dong;
	}

	public String getBonbun() {
		return bonbun;
	}

	public void setBonbun(String bonbun) {
		this.bonbun = bonbun;
	}

	public String getBubun() {
		return bubun;
	}

	public void setBubun(String bubun) {
		this.bubun = bubun;
	}

	public String getSigugunCode() {
		return sigugunCode;
	}

	public void setSigugunCode(String sigugunCode) {
		this.sigugunCode = sigugunCode;
	}

	public String getEubmyungdongCode() {
		return eubmyungdongCode;
	}

	public void setEubmyungdongCode(String eubmyungdongCode) {
		this.eubmyungdongCode = eubmyungdongCode;
	}

	public String getLandCode() {
		return landCode;
	}

	public void setLandCode(String landCode) {
		this.landCode = landCode;
	}

	public String getApartmentName() {
		return apartmentName;
	}

	public void setApartmentName(String apartmentName) {
		this.apartmentName = apartmentName;
	}

	public String getJibun() {
		return jibun;
	}

	public void setJibun(String jibun) {
		this.jibun = jibun;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public int getViewCnt() {
		return viewCnt;
	}

	public void setViewCnt(int viewCnt) {
		this.viewCnt = viewCnt;
	}

	public int getLikeCnt() {
		return likeCnt;
	}

	public void setLikeCnt(int likeCnt) {
		this.likeCnt = likeCnt;
	}

	@Override
	public String toString() {
		return "HouseInfoDto [aptCode=" + aptCode + ", buildYear=" + buildYear + ", roadName=" + roadName
				+ ", roadNameBonbun=" + roadNameBonbun + ", roadNameBubun=" + roadNameBubun + ", roadNameSeq="
				+ roadNameSeq + ", roadNameBasementCode=" + roadNameBasementCode + ", roadNameCode=" + roadNameCode
				+ ", dong=" + dong + ", bonbun=" + bonbun + ", bubun=" + bubun + ", sigugunCode=" + sigugunCode
				+ ", eubmyungdongCode=" + eubmyungdongCode + ", landCode=" + landCode + ", apartmentName="
				+ apartmentName + ", jibun=" + jibun + ", lon=" + lon + ", lat=" + lat + ", viewCnt=" + viewCnt
				+ ", likeCnt=" + likeCnt + "]";
	}

}

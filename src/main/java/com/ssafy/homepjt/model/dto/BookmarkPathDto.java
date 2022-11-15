package com.ssafy.homepjt.model.dto;

public class BookmarkPathDto {
	private int id;
	private String startLat;
	private String startLon;
	private String startAptName;
	private String endLat;
	private String endLon;
	private String endAptName;
	private String memberId;
	private String pathName;

	public BookmarkPathDto() {
		super();
	}

	public BookmarkPathDto(int id, String startLat, String startLon, String startAptName, String endLat, String endLon,
			String endAptName, String memberId, String pathName) {
		super();
		this.id = id;
		this.startLat = startLat;
		this.startLon = startLon;
		this.startAptName = startAptName;
		this.endLat = endLat;
		this.endLon = endLon;
		this.endAptName = endAptName;
		this.memberId = memberId;
		this.pathName = pathName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStartLat() {
		return startLat;
	}

	public void setStartLat(String startLat) {
		this.startLat = startLat;
	}

	public String getStartLon() {
		return startLon;
	}

	public void setStartLon(String startLon) {
		this.startLon = startLon;
	}

	public String getStartAptName() {
		return startAptName;
	}

	public void setStartAptName(String startAptName) {
		this.startAptName = startAptName;
	}

	public String getEndLat() {
		return endLat;
	}

	public void setEndLat(String endLat) {
		this.endLat = endLat;
	}

	public String getEndLon() {
		return endLon;
	}

	public void setEndLon(String endLon) {
		this.endLon = endLon;
	}

	public String getEndAptName() {
		return endAptName;
	}

	public void setEndAptName(String endAptName) {
		this.endAptName = endAptName;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getPathName() {
		return pathName;
	}

	public void setPathName(String pathName) {
		this.pathName = pathName;
	}

	@Override
	public String toString() {
		return "BookmarkPathDto [id=" + id + ", startLat=" + startLat + ", startLon=" + startLon + ", startAptName="
				+ startAptName + ", endLat=" + endLat + ", endLon=" + endLon + ", endAptName=" + endAptName
				+ ", memberId=" + memberId + ", pathName=" + pathName + "]";
	}

}

package com.ssafy.homepjt.model.dto;

public class RecentSearchDto {
	private int id;
	private String memberId;
	private String searchedName;
	private String searchedDate;

	public RecentSearchDto() {
		super();
	}

	public RecentSearchDto(int id, String memberId, String searchedName, String searchedDate) {
		super();
		this.id = id;
		this.memberId = memberId;
		this.searchedName = searchedName;
		this.searchedDate = searchedDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getSearchedName() {
		return searchedName;
	}

	public void setSearchedName(String searchedName) {
		this.searchedName = searchedName;
	}

	public String getSearchedDate() {
		return searchedDate;
	}

	public void setSearchedDate(String searchedDate) {
		this.searchedDate = searchedDate;
	}

	@Override
	public String toString() {
		return "RecentSearchDto [id=" + id + ", memberId=" + memberId + ", searchedName=" + searchedName
				+ ", searchedDate=" + searchedDate + "]";
	}

}

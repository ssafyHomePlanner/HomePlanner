package com.ssafy.homepjt.model.dto;

public class BookmarkAptDto {
	private int id;
	private String memberId;
	private long aptCode;

	public BookmarkAptDto() {
		super();
	}

	public BookmarkAptDto(int id, String memberId, long aptCode) {
		super();
		this.id = id;
		this.memberId = memberId;
		this.aptCode = aptCode;
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

	public long getAptCode() {
		return aptCode;
	}

	public void setAptCode(long aptCode) {
		this.aptCode = aptCode;
	}

	@Override
	public String toString() {
		return "BookmarkAptDto [id=" + id + ", memberId=" + memberId + ", aptCode=" + aptCode + "]";
	}

}

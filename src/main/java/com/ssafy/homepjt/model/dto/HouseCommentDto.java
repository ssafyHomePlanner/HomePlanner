package com.ssafy.homepjt.model.dto;

public class HouseCommentDto {
	private int id;
	private long aptCode;
	private String memberId;
	private String content;
	private String writeDate;

	public HouseCommentDto() {
		super();
	}

	public HouseCommentDto(int id, long aptCode, String memberId, String content, String writeDate) {
		super();
		this.id = id;
		this.aptCode = aptCode;
		this.memberId = memberId;
		this.content = content;
		this.writeDate = writeDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getAptCode() {
		return aptCode;
	}

	public void setAptCode(long aptCode) {
		this.aptCode = aptCode;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}

	@Override
	public String toString() {
		return "HouseCommentDto [id=" + id + ", aptCode=" + aptCode + ", memberId=" + memberId + ", content=" + content
				+ ", writeDate=" + writeDate + "]";
	}

}

package com.ssafy.homepjt.model.dto;

public class BoardDto {
	private int id;
	private String memberId;
	private String title;
	private String content;
	private int viewCnt;
	private int likeCnt;
	private String writeDate;

	public BoardDto() {
		super();
	}

	public BoardDto(int id, String memberId, String title, String content, int viewCnt, int likeCnt, String writeDate) {
		super();
		this.id = id;
		this.memberId = memberId;
		this.title = title;
		this.content = content;
		this.viewCnt = viewCnt;
		this.likeCnt = likeCnt;
		this.writeDate = writeDate;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public String getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}

	@Override
	public String toString() {
		return "BoardDto [id=" + id + ", memberId=" + memberId + ", title=" + title + ", content=" + content
				+ ", viewCnt=" + viewCnt + ", likeCnt=" + likeCnt + ", writeDate=" + writeDate + "]";
	}

}

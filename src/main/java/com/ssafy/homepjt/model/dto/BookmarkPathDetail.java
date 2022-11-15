package com.ssafy.homepjt.model.dto;

public class BookmarkPathDetail {
	private int id;
	private int bookmarkPathId;
	private long aptCode;
	private int idx;

	public BookmarkPathDetail() {
		super();
	}

	public BookmarkPathDetail(int id, int bookmarkPathId, long aptCode, int idx) {
		super();
		this.id = id;
		this.bookmarkPathId = bookmarkPathId;
		this.aptCode = aptCode;
		this.idx = idx;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBookmarkPathId() {
		return bookmarkPathId;
	}

	public void setBookmarkPathId(int bookmarkPathId) {
		this.bookmarkPathId = bookmarkPathId;
	}

	public long getAptCode() {
		return aptCode;
	}

	public void setAptCode(long aptCode) {
		this.aptCode = aptCode;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	@Override
	public String toString() {
		return "BookmarkPathDetail [id=" + id + ", bookmarkPathId=" + bookmarkPathId + ", aptCode=" + aptCode + ", idx="
				+ idx + "]";
	}

}

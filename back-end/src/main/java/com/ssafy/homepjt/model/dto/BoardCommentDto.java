package com.ssafy.homepjt.model.dto;

public class BoardCommentDto {
	private int id;
	private int boardId;
	private String memberId;
	private String content;
	private String writeDate;

	public BoardCommentDto() {
		super();
	}

	public BoardCommentDto(int id, int boardId, String memberId, String content, String writeDate) {
		super();
		this.id = id;
		this.boardId = boardId;
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

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
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
		return "BoardCommentDto [id=" + id + ", boardId=" + boardId + ", memberId=" + memberId + ", content=" + content
				+ ", writeDate=" + writeDate + "]";
	}

}

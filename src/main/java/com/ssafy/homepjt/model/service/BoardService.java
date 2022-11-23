package com.ssafy.homepjt.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.homepjt.model.dto.BoardCommentDto;
import com.ssafy.homepjt.model.dto.BoardDto;

public interface BoardService {
	public Map<String, Object> listBoard(int page) throws SQLException; // 전체 게시판 목록

	public void writeBoard(BoardDto boardDto) throws SQLException; // 게시판 글쓰기

	public void updateReadCount(int boardId) throws SQLException; // 조회수 수정

	public void updateLikeCount(int boardId, String memberId, boolean flag) throws SQLException; // 좋아요 수 수정

	public boolean checkBoardLike(int boardId, String memberId) throws SQLException; // 좋아요 확인

	public void updateBoard(BoardDto boardDto) throws SQLException; // 게시판 글 수정

	public void deleteBoard(int boardId) throws SQLException; // 게시판 글 삭제

	public List<BoardCommentDto> listBoardComment(int boardId) throws SQLException; // 게시판 댓글 목록

	public void writeBoardComment(BoardCommentDto boardCommentDto) throws SQLException; // 게시판 댓글 쓰기

	public void updateBoardComment(BoardCommentDto boardCommentDto) throws SQLException; // 게시판 댓글 수정

	public void deleteBoardCommentDto(int boardCommentId) throws SQLException; // 게시판 댓글 삭제

}

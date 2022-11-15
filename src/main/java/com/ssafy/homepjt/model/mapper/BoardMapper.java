package com.ssafy.homepjt.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.homepjt.model.dto.BoardCommentDto;
import com.ssafy.homepjt.model.dto.BoardDto;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BoardMapper {

	public List<BoardDto> listBoard(@Param("start") int startRow, @Param("cnt") int count) throws SQLException; // 전체 게시판 목록
	
	public int selectTotalCount(); // 게시글 총 개수

	public void writeBoard(BoardDto boardDto) throws SQLException; // 게시판 글쓰기

	public BoardDto readBoard(int boardId) throws SQLException; // 게시판 글 세부내용 보기

	public void updateBoard(BoardDto boardDto) throws SQLException; // 게시판 글 수정

	public void deleteBoard(int boardID) throws SQLException; // 게시판 글 삭제

	public List<BoardCommentDto> listBoardComment(int boardId) throws SQLException; // 게시판 댓글 목록

	public void writeBoardComment(BoardCommentDto boardCommentDto) throws SQLException; // 게시판 댓글 쓰기

	public void updateBoardComment(BoardCommentDto boardCommentDto) throws SQLException; // 게시판 댓글 수정

	public void deleteBoardComment(int boardCommentId) throws SQLException; // 게시판 댓글 삭제

}

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

    public void updateReadCount(int boardId) throws SQLException; // 조회수 변경

    public void updateLikeCount(@Param("boardId") int boardId) throws SQLException; // 좋아요 수 변경

    // 게시글 회원 죻아요 여부 확인
    public int checkBoardLike(@Param("boardId") int boardId, @Param("memberId") String memberId) throws SQLException;

    // 게시글 좋아요 회원 추가
    public void insertBoardLike(@Param("boardId") int boardId, @Param("memberId") String memberId) throws SQLException;

    // 게시글 좋아요 회원 삭제
    public void deleteBoardLike(@Param("boardId") int boardId, @Param("memberId") String memberId) throws SQLException;

    public void updateBoard(BoardDto boardDto) throws SQLException; // 게시판 글 수정

    public void deleteBoard(int boardId) throws SQLException; // 게시판 글 삭제

    public List<BoardCommentDto> listBoardComment(int boardId) throws SQLException; // 게시판 댓글 목록

    public void writeBoardComment(BoardCommentDto boardCommentDto) throws SQLException; // 게시판 댓글 쓰기

    public void updateBoardComment(BoardCommentDto boardCommentDto) throws SQLException; // 게시판 댓글 수정

    public void deleteBoardComment(int boardCommentId) throws SQLException; // 게시판 댓글 삭제

}

package com.ssafy.homepjt.model.service;

import com.ssafy.homepjt.model.dto.BookmarkPathDto;
import com.ssafy.homepjt.model.dto.HouseInfoDto;
import com.ssafy.homepjt.model.request.BookmarkPathRequestDto;
import com.ssafy.homepjt.model.response.BookmarkPathResponseDto;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface BookmarkService {
    // 관심 상품 등록
    public void insertBookmarkApt(@Param("memberId") String memberId, @Param("aptCode") long aptCode);

    // 관심 상품 목록 보기
    public List<HouseInfoDto> selectBookmarkApt(String memberId) throws SQLException;

    // 관심 상품 목록 보기
    public void deleteBookmarkApt(String memberId, long aptCode) throws SQLException;

    // 게시글 좋아요 회원 등록
    public void insertBookmarkMember(@Param("boardId") int boardId, @Param("memberId") String memberId);

    // 게시글 좋아요 클릭 여부 확인
    public int checkBoardMemberLike(@Param("boardId") int boardId, @Param("memberId") String memberId);


    // 경로 탐색
    public Map<String, Object> searchPath(List<BookmarkPathRequestDto> bookmarkPathRequestDtoList);

    // 관심 경로 불러오기(출발지, 도착지)
    public List<BookmarkPathDto> searchBookmarkPath(String memberId);

    // 관심 경로 불러오기(경유지)
    public List<BookmarkPathResponseDto> searchBookmarkPathDetail(int bookmarkPathId);

    // 관심 경로 등록
    void insertBookmarkPath(List<BookmarkPathRequestDto> bookmarkPathRequestDtoList, String memberId, String pathName);

    // 관심 경로 삭제
    void deleteBookmarkPath(int bookmarkPathId);
}

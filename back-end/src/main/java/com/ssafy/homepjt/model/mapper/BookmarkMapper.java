package com.ssafy.homepjt.model.mapper;

import com.ssafy.homepjt.model.dto.BookmarkPathDetailDto;
import com.ssafy.homepjt.model.dto.BookmarkPathDto;
import com.ssafy.homepjt.model.dto.HouseInfoDto;
import com.ssafy.homepjt.model.request.BookmarkPathRequestDto;
import com.ssafy.homepjt.model.response.BookmarkPathResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookmarkMapper {
    // 관심 상품 등록
    public void insertBookmarkApt(@Param("memberId") String memberId, @Param("aptCode") long aptCode);

    // 관심 상품 목록 보기
    public List<HouseInfoDto> selectBookmarkApt(String memberId);

    // 관심 상품 삭제
    public void deleteBookmarkApt(@Param("memberId") String memberId, @Param("aptCode") long aptCode);

    // 게시글 좋아요 회원 등록
    public void insertBookmarkMember(@Param("boardId") int boardId, @Param("memberId") String memberId);

    // 게시글 좋아요 클릭 여부 확인
    public int checkBoardMemberLike(@Param("boardId") int boardId, @Param("memberId") String memberId);

    // 관심 경로 불러오기(출발지, 도착지)
    public List<BookmarkPathDto> searchBookmarkPath(String memberId);

    // 관심 경로 불러오기(경유지)
    public List<BookmarkPathResponseDto> searchBookmarkPathDetail(int bookmarkPathId);

    // 관심 경로 등록(출발지, 도착지)
    public void insertBookmarkPath(BookmarkPathDto bookmarkPathDto);
    // 관심 경로 등록(경유지)
    public void insertBookmarkPathDetail(BookmarkPathDetailDto bookmarkPathDetailDto);

    // 관심 경로 삭제
    public void deleteBookmarkPath(int bookmarkPathId);
}
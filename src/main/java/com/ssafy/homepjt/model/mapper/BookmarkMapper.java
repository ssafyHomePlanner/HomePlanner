package com.ssafy.homepjt.model.mapper;

import com.ssafy.homepjt.model.dto.HouseInfoDto;
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
    public void deleteBookmarkApt(int bookmarkId);

    // 게시글 좋아요 회원 등록
    public void insertBookmarkMember(@Param("boardId") int boardId, @Param("memberId") String memberId);

    // 게시글 좋아요 클릭 여부 확인
    public int checkBoardMemberLike(@Param("boardId") int boardId, @Param("memberId") String memberId);

    // 관심 경로 목록 보기
}
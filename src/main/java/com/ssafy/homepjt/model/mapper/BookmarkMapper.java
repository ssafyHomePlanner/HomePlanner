package com.ssafy.homepjt.model.mapper;

import com.ssafy.homepjt.model.dto.HouseInfoDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookmarkMapper {
    // 관심 상품 등록
    public void insertBookmarkApt(@Param("memberId") String memberId, @Param("aptCode") String aptCode);
    // 관심 상품 목록 보기
    public List<HouseInfoDto> selectBookmarkApt(String memberId);

    // 관심 상품 삭제
    public void deleteBookmarkApt(int bookmarkId);

    // 관심 경로 목록 보기
}
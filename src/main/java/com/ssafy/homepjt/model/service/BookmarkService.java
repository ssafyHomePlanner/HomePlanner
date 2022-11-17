package com.ssafy.homepjt.model.service;

import com.ssafy.homepjt.model.dto.HouseInfoDto;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.List;

public interface BookmarkService {
    // 관심 상품 등록
    public void insertBookmarkApt(@Param("memberId") String memberId, @Param("aptCode") String aptCode);

    // 관심 상품 목록 보기
    public List<HouseInfoDto> selectBookmarkApt(String memberId) throws SQLException;

    // 관심 상품 목록 보기
    public void deleteBookmarkApt(int bookmarkAptId) throws SQLException;

    // 관심 경로 목록 보기
}

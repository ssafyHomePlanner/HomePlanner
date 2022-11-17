package com.ssafy.homepjt.model.service;

import com.ssafy.homepjt.model.dto.HouseCommentDto;
import com.ssafy.homepjt.model.dto.HouseDealDto;
import com.ssafy.homepjt.model.dto.HouseInfoDto;
import com.ssafy.homepjt.model.request.AptSearchRequestDto;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface HouseInfoService {

    // 아파트 검색
    Map<String, Object> selectHouseInfo(AptSearchRequestDto aptSearchRequestDto, int page);

    // 아파트 조회수 증가
    void updateReadCount(long aptCode) throws SQLException;

    // 아파트 검색(자동완성용)
    List<HouseInfoDto> selectHouseInfoAuto(String aptName) throws SQLException;

    // 아파트 상세 정보 검색
    List<HouseDealDto> selectHouseDeal(long aptCode) throws SQLException;

    // 아파트 댓글 조회
    List<HouseCommentDto> selectHouseComment(long aptCode) throws SQLException;

    // 아파트 댓글 쓰기
    void writeHouseComment(HouseCommentDto houseCommentDto) throws SQLException;

    // 아파트 댓글 수정
    void updateHouseComment(HouseCommentDto houseCommentDto) throws SQLException;

    // 아파트 댓글 삭제
    void deleteHouseComment(int houseCommentId) throws SQLException;
}
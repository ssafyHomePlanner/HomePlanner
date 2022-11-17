package com.ssafy.homepjt.model.mapper;

import com.ssafy.homepjt.model.dto.HouseCommentDto;
import com.ssafy.homepjt.model.dto.HouseDealDto;
import com.ssafy.homepjt.model.dto.HouseInfoDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface HouseInfoMapper {

    public int selectTotalCount(@Param("sidoName") String sidoName, @Param("gugunName") String gugunName,
                                @Param("dongName") String dongName, @Param("aptName") String aptName); // 검색된 아파트 개수

    // 아파트 검색
    List<HouseInfoDto> selectHouseInfo(@Param("sidoName") String sidoName, @Param("gugunName") String gugunName,
                                       @Param("dongName") String dongName, @Param("aptName") String aptName,
                                        @Param("start") int startRow, @Param("cnt") int count);

    void updateReadCount(long aptCode) throws SQLException;

    // 아파트 검색(자동완성용)
    List<HouseInfoDto> selectHouseInfoAuto(String aptName);

    // 아파트 상세 정보 검색
    List<HouseDealDto> selectHouseDeal(long aptCode);

    // 아파트 댓글 조회
    List<HouseCommentDto> selectHouseComment(long aptCode);

    // 아파트 댓글 쓰기
    void writeHouseComment(HouseCommentDto houseCommentDto);

    // 아파트 댓글 수정
    void updateHouseComment(HouseCommentDto houseCommentDto);

    // 아파트 댓글 삭제
    void deleteHouseComment(int houseCommentId);

}

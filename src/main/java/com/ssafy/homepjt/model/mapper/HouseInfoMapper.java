package com.ssafy.homepjt.model.mapper;

import com.ssafy.homepjt.model.dto.HouseDealDto;
import com.ssafy.homepjt.model.dto.HouseInfoDto;
import com.ssafy.homepjt.model.request.AptSearchRequestDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

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

    void updateReadCount(String aptCode) throws SQLException;

    // 아파트 검색(자동완성용)
    List<HouseInfoDto> selectHouseInfoAuto(String aptName);

    // 아파트 상세 정보 검색
    List<HouseDealDto> selectHouseDeal(String aptCode);

}

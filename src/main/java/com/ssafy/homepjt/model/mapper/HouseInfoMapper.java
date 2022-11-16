package com.ssafy.homepjt.model.mapper;

import com.ssafy.homepjt.model.dto.HouseInfoDto;
import com.ssafy.homepjt.model.request.AptSearchRequestDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HouseInfoMapper {
    // 아파트 검색
    List<HouseInfoDto> selectHouseInfo(@Param("sidoName") String sidoName, @Param("gugunName") String gugunName,
                                       @Param("dongName") String dongName, @Param("aptName") String aptName);

    // 아파트 검색(자동완성용)
    List<HouseInfoDto> selectHouseInfoAuto(String aptName);

}

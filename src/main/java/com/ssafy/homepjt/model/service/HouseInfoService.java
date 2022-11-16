package com.ssafy.homepjt.model.service;

import com.ssafy.homepjt.model.dto.HouseInfoDto;
import com.ssafy.homepjt.model.request.AptSearchRequestDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface HouseInfoService {

    // 아파트 검색
    List<HouseInfoDto> selectHouseInfo(AptSearchRequestDto aptSearchRequestDto);

    // 아파트 검색(자동완성용)
    List<HouseInfoDto> selectHouseInfoAuto(String aptName);

}
package com.ssafy.homepjt.model.service;

import com.ssafy.homepjt.model.dto.HouseInfoDto;
import com.ssafy.homepjt.model.mapper.HouseInfoMapper;
import com.ssafy.homepjt.model.request.AptSearchRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseInfoServiceImpl implements HouseInfoService {

    @Autowired
    private HouseInfoMapper houseInfoMapper;


    @Override
    public List<HouseInfoDto> selectHouseInfo(AptSearchRequestDto aptSearchRequestDto) {
        String sidoName = aptSearchRequestDto.getSidoName() == null ? "" : aptSearchRequestDto.getSidoName();
        String gugunName = aptSearchRequestDto.getGugunName() == null ? "" : aptSearchRequestDto.getGugunName();
        String dongName = aptSearchRequestDto.getDongName() == null ? "" : aptSearchRequestDto.getDongName();
        String aptName = aptSearchRequestDto.getAptName() == null ? "" : aptSearchRequestDto.getAptName();
        return houseInfoMapper.selectHouseInfo(sidoName, gugunName, dongName, aptName);
    }

    @Override
    public List<HouseInfoDto> selectHouseInfoAuto(String aptName) {
        return houseInfoMapper.selectHouseInfoAuto(aptName);
    }
}

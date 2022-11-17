package com.ssafy.homepjt.model.service;

import com.ssafy.homepjt.model.dto.HouseDealDto;
import com.ssafy.homepjt.model.dto.HouseInfoDto;
import com.ssafy.homepjt.model.mapper.HouseInfoMapper;
import com.ssafy.homepjt.model.request.AptSearchRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HouseInfoServiceImpl implements HouseInfoService {

    private static final int COUNT_PER_PAGE = 10;
    @Autowired
    private HouseInfoMapper houseInfoMapper;


    @Override
    public Map<String, Object> selectHouseInfo(AptSearchRequestDto aptSearchRequestDto, int page) {
        String sidoName = aptSearchRequestDto.getSidoName() == null ? "" : aptSearchRequestDto.getSidoName();
        String gugunName = aptSearchRequestDto.getGugunName() == null ? "" : aptSearchRequestDto.getGugunName();
        String dongName = aptSearchRequestDto.getDongName() == null ? "" : aptSearchRequestDto.getDongName();
        String aptName = aptSearchRequestDto.getAptName() == null ? "" : aptSearchRequestDto.getAptName();

        int totalCount = houseInfoMapper.selectTotalCount(sidoName, gugunName, dongName, aptName);
        int totalPage = totalCount / COUNT_PER_PAGE;
        if (totalPage % COUNT_PER_PAGE > 0) {
            totalPage++;
        }

        int startPage = (page - 1) / 10 * 10 + 1;
        int endPage = startPage + 9;
        if (endPage > totalPage) {
            endPage = totalPage;
        }

        int startRow = (page - 1) * COUNT_PER_PAGE;
        List<HouseInfoDto> houseInfoDtoList = houseInfoMapper.selectHouseInfo(sidoName, gugunName, dongName, aptName, startRow, COUNT_PER_PAGE);

        Map<String, Object> result = new HashMap<>();
        result.put("startPage", startPage);
        result.put("endPage", endPage);
        result.put("currPage", page);
        result.put("totalPage", totalPage);
        result.put("houseInfoList", houseInfoDtoList);
        return result;
    }

    @Override
    public void updateReadCount(String aptCode) throws SQLException {
        houseInfoMapper.updateReadCount(aptCode);
    }

    @Override
    public List<HouseInfoDto> selectHouseInfoAuto(String aptName) {
        return houseInfoMapper.selectHouseInfoAuto(aptName);
    }

    @Override
    public List<HouseDealDto> selectHouseDeal(String aptCode) {
        return houseInfoMapper.selectHouseDeal(aptCode);
    }
}

package com.ssafy.homepjt.model.service;

import com.ssafy.homepjt.model.dto.DongCodeDto;
import com.ssafy.homepjt.model.mapper.DongCodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class DongCodeServiceImpl implements DongCodeService{

    @Autowired
    private DongCodeMapper dongCodeMapper;

    @Override
    public List<String> selectSidoName() throws SQLException {
        return dongCodeMapper.selectSidoName();
    }

    @Override
    public List<String> selectGugunName(String sidoName) throws SQLException {
        return dongCodeMapper.selectGugunName(sidoName);
    }

    @Override
    public List<String> selectDongName(String gugunName) throws SQLException {
        return dongCodeMapper.selectDongName(gugunName);
    }

    @Override
    public DongCodeDto selectDongCodeInfo(String dongCode) throws SQLException {
        return dongCodeMapper.selectDongCodeInfo(dongCode);
    }
}

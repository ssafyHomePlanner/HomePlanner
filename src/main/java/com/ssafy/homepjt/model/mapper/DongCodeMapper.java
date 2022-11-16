package com.ssafy.homepjt.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface DongCodeMapper {

    // 시도 리스트
    public List<String> selectSidoName() throws SQLException;
    // 시군구 리스트
    public List<String> selectGugunName(String sidoName) throws SQLException;
    // 읍면동 리스트
    public List<String> selectDongName(String gugunName) throws SQLException;
}

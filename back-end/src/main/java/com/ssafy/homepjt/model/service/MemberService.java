package com.ssafy.homepjt.model.service;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import com.ssafy.homepjt.model.dto.RecentDto;
import org.apache.ibatis.annotations.Param;

import com.ssafy.homepjt.model.dto.MemberDto;

public interface MemberService {
    public int idCheck(String id) throws SQLException; // 아이디 중복 확인

    public void join(MemberDto memberDto) throws SQLException, NoSuchAlgorithmException; // 회원가입

    public MemberDto login(@Param("memberId") String memberId, @Param("memberPw") String memberPw)
            throws SQLException, NoSuchAlgorithmException; // 로그인

    public void saveRefreshToken(String memberId, String refreshToken) throws Exception;

    public Object getRefreshToken(String memberId) throws Exception;

    public void deleteRefreshToken(String memberId) throws Exception;

    public MemberDto detail(String id) throws SQLException; // 회원 상세 정보

    public void update(MemberDto memberDto) throws SQLException; // 회원 정보 수정

    public void delete(String memberId) throws SQLException; // 회원 삭제

    public String findId(@Param("memberName") String memberName, @Param("memberPhone") String memberPhone); // 아이디

    public String findPw(@Param("memberId") String memberId, @Param("memberPhone") String memberPhone); // 비밀번호 찾기

    // 회원 최근 검색 등록
    public void insertRecentData(@Param("memberId") String memberId, @Param("data") String data) throws SQLException;

    // 회원 최근 검색
    public List<RecentDto> searchRecentData(String memberId) throws SQLException;

    // 회원 최근 검색 삭제
    public void deleteRecentData(@Param("memberId") String memberId, @Param("recentId") String recentId) throws SQLException;

    // 회원 최근 검색 전체 삭제
    public void deleteRecentDataAll(String memberId) throws SQLException;

}

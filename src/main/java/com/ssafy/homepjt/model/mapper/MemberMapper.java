package com.ssafy.homepjt.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.homepjt.model.dto.RecentDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.homepjt.model.dto.MemberDto;

@Mapper
public interface MemberMapper {
	public int idCheck(String id) throws SQLException; // 아이디 중복 확인

	public void join(MemberDto memberDto) throws SQLException; // 회원가입

	public String findSalt(String memberId) throws SQLException; // 해당 회원의 salt값 가져오기
	
	public MemberDto login(@Param("memberId") String memberId, @Param("memberPw") String memberPw)
			throws SQLException; // 로그인

	public void saveRefreshToken(Map<String, String> map) throws SQLException;

	Object getRefreshToken(String memberId) throws SQLException;

	public void deleteRefreshToken(Map<String, String> map) throws SQLException;

	public MemberDto detail(String memberId) throws SQLException; // 회원 상세 정보

	public void update(MemberDto memberDto) throws SQLException; // 회원 정보 수정

	public void delete(String memberId) throws SQLException; // 회원 삭제

	public String findId(@Param("memberName") String name, @Param("memberPhone") String memherPhone); // 아이디

	public String findPw(@Param("memberId") String memberId, @Param("memberPhone") String memberPhone); // 비밀번호 찾기

	public int checkRecentData(@Param("memberId") String memberId, @Param("data") String data);

	// 회원 최근 검색 등록
	public void insertRecentData(String memberId, String data) throws SQLException;

	// 회원 최근 검색
	public List<RecentDto> searchRecentData(String memberId) throws SQLException;

	// 회원 최근 검색 삭제
	public void deleteRecentData(@Param("memberId") String memberId, @Param("recentId") String recentId) throws SQLException;

	// 회원 최근 검색 전체 삭제
	public void deleteRecentDataAll(String memberId) throws SQLException;

}

package com.ssafy.homepjt.model.mapper;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.homepjt.model.dto.MemberDto;

@Mapper
public interface MemberMapper {
	public int idCheck(String id) throws SQLException; // 아이디 중복 확인

	public void join(MemberDto memberDto) throws SQLException; // 회원가입

	public MemberDto login(@Param("memberId") String memberId, @Param("memberPw") String memberPw)
			throws SQLException; // 로그인

	public MemberDto detail(String memberId) throws SQLException; // 회원 상세 정보

	public void update(MemberDto memberDto) throws SQLException; // 회원 정보 수정

	public void delete(String memberId) throws SQLException; // 회원 삭제

	public String findId(@Param("memberName") String name, @Param("memberPhone") String memherPhone); // 아이디

	public String findPw(@Param("memberId") String memberId, @Param("memberPhone") String memberPhone); // 비밀번호 찾기

	// 관심 상품 목록 보기
	// 관심 경로 목록 보기
}

package com.ssafy.homepjt.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.homepjt.model.dto.MemberDto;
import com.ssafy.homepjt.model.service.MemberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/member")
@Api("사용자 컨트롤러 API V1")
public class MemberController {

	public static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private MemberService memberService;

	// 아이디 중복 확인(회원가입 진행 중, 비동기로 확인)
	@ApiOperation(value = "아이디 중복 확인")
	@ApiResponses({ @ApiResponse(code = 200, message = "아이디 중복 확인 성공!!"),
			@ApiResponse(code = 404, message = "잘못된 접근!!"), @ApiResponse(code = 500, message = "서버에러!!") })
	@GetMapping("/idCheck/{memberId}")
	public ResponseEntity<Map<String, Object>> idCheck(@PathVariable("memberId") String memberId) {
		logger.info("member idCheck controller, memberId : {}", memberId);
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			if (memberService.idCheck(memberId) != 0) {
				logger.debug("중복 아이디 확인!!");
				resultMap.put("message", FAIL);
			} else {
				logger.debug("중복 아이디 존재하지 않음!!");
				resultMap.put("message", SUCCESS);
			}
			return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			logger.error("중복 아이디 검색 실패 : {}", e.getMessage());
			resultMap.put("message", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 회원가입 + 아이디 중복 확인
	@ApiOperation(value = "회원가입")
	@ApiResponses({ @ApiResponse(code = 200, message = "회원가입 성공!!"), @ApiResponse(code = 404, message = "잘못된 접근!!"),
			@ApiResponse(code = 500, message = "서버에러!!") })
	@PostMapping("/join")
	public ResponseEntity<Map<String, Object>> join(MemberDto memberDto) {
		logger.info("member join controller, memberDto info : {}", memberDto);

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			if (memberService.idCheck(memberDto.getId()) != 0) {
				logger.debug("중복 아이디 확인!!!");
				resultMap.put("message", FAIL);
				return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.BAD_REQUEST);
			} else {
				memberService.join(memberDto);
				resultMap.put("message", SUCCESS);
				return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.ACCEPTED);
			}

		} catch (Exception e) {
			logger.error("회원가입 실패 : {}", e.getMessage());
			resultMap.put("message", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 로그인
	@ApiOperation(value = "로그인")
	@ApiResponses({ @ApiResponse(code = 200, message = "로그인 성공!!"), @ApiResponse(code = 404, message = "잘못된 접근!!"),
			@ApiResponse(code = 500, message = "서버에러!!") })
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(
			@ApiParam(value = "로그인 시 필요한 회원정보(아이디, 비밀번호).", required = true) MemberDto memberDto) {
		logger.info("member login controller, memberDto info : {}", memberDto);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			MemberDto loginMember = memberService.login(memberDto.getId(), memberDto.getPw());
			if (loginMember != null) {
				logger.debug("로그인 성공 : {}", memberDto);
				resultMap.put("message", SUCCESS);
				return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.ACCEPTED);
			} else {
				logger.debug("로그인 실패 : 아이디 혹은 비밀번호 불일치");
				resultMap.put("message", FAIL);
				return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			logger.error("로그인 실패 : {}", e.getMessage());
			resultMap.put("message", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	// 로그아웃
	

	// 회원 상세 정보
	@ApiOperation(value = "회원상세 정보 보기")
	@ApiResponses({ @ApiResponse(code = 200, message = "회원상세 정보 보기 성공!!"),
			@ApiResponse(code = 404, message = "잘못된 접근!!"), @ApiResponse(code = 500, message = "서버에러!!") })
	@GetMapping("/detail/{memberId}")
	public ResponseEntity<Map<String, Object>> detail(@PathVariable("memberId") String memberId) {
		logger.debug("member detail controller, memberId : {}", memberId);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			MemberDto memberDto = memberService.detail(memberId);
			if (memberDto != null) {
				logger.debug("회원조회 성공 : {}", memberDto);
				resultMap.put("message", SUCCESS);
				resultMap.put("memberDto", memberDto);
				return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.ACCEPTED);
			} else {
				logger.debug("회원조회 실패 : {}");
				resultMap.put("message", FAIL);
				resultMap.put("memberDto", null);
				return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.error("회원정보 조회 실패 : {}", e.getMessage());
			resultMap.put("message", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 회원 정보 수정
	@ApiOperation(value = "회원 정보 수정")
	@ApiResponses({ @ApiResponse(code = 200, message = "회원 정보 수정 성공!!"), @ApiResponse(code = 404, message = "잘못된 접근!!"),
			@ApiResponse(code = 500, message = "서버에러!!") })
	@PutMapping("/update")
	public ResponseEntity<Map<String, Object>> update(MemberDto memberDto) {
		logger.debug("member update controller, memberDto : {}", memberDto);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			memberService.update(memberDto);
			MemberDto updatedMemberDto = memberService.detail(memberDto.getId());
			logger.debug("회원정보 수정 성공 : {}", updatedMemberDto);
			resultMap.put("message", SUCCESS);
			resultMap.put("memberDto", updatedMemberDto);
			return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.ACCEPTED);

		} catch (Exception e) {
			logger.error("회원정보 수정 실패 : {}", e.getMessage());
			resultMap.put("message", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 회원 삭제
	@ApiOperation(value = "회원 삭제")
	@ApiResponses({ @ApiResponse(code = 200, message = "회원 삭제 성공!!"), @ApiResponse(code = 404, message = "잘못된 접근!!"),
			@ApiResponse(code = 500, message = "서버에러!!") })
	@DeleteMapping("/delete/{memberId}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable("memberId") String memberId) {
		logger.debug("member delete controller, memberId : {}", memberId);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			memberService.delete(memberId);
			logger.debug("회원 삭제 성공 : {}", memberId);
			resultMap.put("message", SUCCESS);
			return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.ACCEPTED);

		} catch (Exception e) {
			logger.error("회원 삭제 실패 : {}", e.getMessage());
			resultMap.put("message", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 아이디 찾기
	@ApiOperation(value = "회원 아이디 찾기")
	@ApiResponses({ @ApiResponse(code = 200, message = "회원 아이디 찾기 성공!!"),
			@ApiResponse(code = 404, message = "잘못된 접근!!"), @ApiResponse(code = 500, message = "서버에러!!") })
	@GetMapping("/findId/{memberName}/{memberPhone}")
	public ResponseEntity<Map<String, Object>> findId(@PathVariable("memberName") String memberName,
			@PathVariable("memberPhone") String memberPhone) {
		logger.debug("member findId controller, memberName, memberPhone : {}", memberName, memberPhone);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {

			String findMemberId = memberService.findId(memberName, memberPhone);
			if (findMemberId != null) {
				logger.debug("회원 아이디 찾기 성공 : {}", findMemberId);
				resultMap.put("message", SUCCESS);
				resultMap.put("memberId", findMemberId);
				return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.ACCEPTED);
			} else {
				logger.debug("회원 아이디 찾기 실패 : 이름, 번호를 확인해주세요");
				resultMap.put("message", FAIL);
				resultMap.put("memberId", null);
				return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			logger.error("회원 아이디 찾기 실패 : {}", e.getMessage());
			resultMap.put("message", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 비밀번호 찾기
	@ApiOperation(value = "회원 비밀번호 찾기")
	@ApiResponses({ @ApiResponse(code = 200, message = "회원 비밀번호 찾기 성공!!"),
			@ApiResponse(code = 404, message = "잘못된 접근!!"), @ApiResponse(code = 500, message = "서버에러!!") })
	@GetMapping("/findPw/{memberId}/{memberPhone}")
	public ResponseEntity<Map<String, Object>> findPw(@PathVariable("memberId") String memberId,
			@PathVariable("memberPhone") String memberPhone) {
		logger.debug("member findPwcontroller, memberId, memberPhone : {}", memberId, memberPhone);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {

			String findMemberPw = memberService.findPw(memberId, memberPhone);
			if (findMemberPw != null) {
				logger.debug("회원 비밀번호 찾기 성공 : {}", findMemberPw);
				resultMap.put("message", SUCCESS);
				resultMap.put("memberId", findMemberPw);
				return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.ACCEPTED);
			} else {
				logger.debug("회원 비밀번호 찾기 실패 : 아이디, 번호를 확인해주세요");
				resultMap.put("message", FAIL);
				resultMap.put("memberPw", null);
				return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			logger.error("회원 비밀번호 찾기 실패 : {}", e.getMessage());
			resultMap.put("message", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	// 관심 상품 목록 보기

	// 관심 경로 목록 보기
}

package com.ssafy.homepjt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssafy.homepjt.model.dto.RecentDto;
import com.ssafy.homepjt.model.service.JwtServiceImpl;
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

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/member")
@Api("사용자 컨트롤러 API V1")
public class MemberController {

    public static final Logger logger = LoggerFactory.getLogger(MemberController.class);
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private MemberService memberService;

    @Autowired
    private JwtServiceImpl jwtService;

    // 아이디 중복 확인(회원가입 진행 중, 비동기로 확인)
    @ApiOperation(value = "아이디 중복 확인")
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
    @PostMapping("/join")
    public ResponseEntity<Map<String, Object>> join(@RequestBody MemberDto memberDto) {
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
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(
            @ApiParam(value = "로그인 시 필요한 회원정보(아이디, 비밀번호).", required = true) @RequestBody MemberDto memberDto) {
        logger.info("member login controller, memberDto info : {}", memberDto);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            MemberDto loginMember = memberService.login(memberDto.getId(), memberDto.getPw());
            if (loginMember != null) {

                String accessToken = jwtService.createAccessToken("memberId", loginMember.getId()); //key,data
                String refreshToken = jwtService.createRefreshToken("memberId", loginMember.getId()); //key,data
                memberService.saveRefreshToken(memberDto.getId(), refreshToken);
                logger.debug("로그인 accessToken 정보 : {}", accessToken);
                logger.debug("로그인 refreshToken 정보 : {}", refreshToken);

                resultMap.put("access-token", accessToken);
                resultMap.put("refresh-token", refreshToken);
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

    @ApiOperation(value = "회원인증")
    @GetMapping("/info/{memberId}")
    public ResponseEntity<Map<String, Object>> getInfo(
            @PathVariable("memberId") String memberId, HttpServletRequest request
    ) {
        Map<String, Object> resultMap = new HashMap<>();
        if (jwtService.checkToken(request.getHeader("access-token"))) {
            logger.info("사용 가능한 토큰!!");
            try {
                //로그인 사용자 정보
                MemberDto memberDto = memberService.detail(memberId);
                resultMap.put("memberInfo", memberDto);
                resultMap.put("message", SUCCESS);
                return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);
            } catch (Exception e) {
                logger.error("회원 정보 조회 실패 : {}", e);
                resultMap.put("message", e.getMessage());
                return new ResponseEntity<>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            logger.error("사용 불가능한 토큰!!");
            resultMap.put("message", FAIL);
            return new ResponseEntity<>(resultMap, HttpStatus.UNAUTHORIZED);
        }
    }

    // 로그아웃
    @ApiOperation(value = "로그아웃")
    @GetMapping("/logout/{memberId}")
    public ResponseEntity<?> removeToken(@PathVariable("memberId") String memberId) {
        Map<String, Object> resultMap = new HashMap<>();

        try {
            memberService.deleteRefreshToken(memberId);
            resultMap.put("message", SUCCESS);
            return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            logger.error("로그아웃 실패 : {}", e);
            resultMap.put("message", e.getMessage());
            return new ResponseEntity<>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Access Token 재발급
    @ApiOperation(value = "Access Token 재발급")
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody MemberDto memberDto, HttpServletRequest request) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        String token = request.getHeader("refresh-token");
        logger.debug("token : {}, memberDto : {}", token, memberDto);

        if (jwtService.checkToken(token)) {
            if (token.equals(memberService.getRefreshToken(memberDto.getId()))) {
                String accessToken = jwtService.createAccessToken("memberId", memberDto.getId());
                logger.debug("token : {}", accessToken);
                logger.debug("정상적으로 액세스토큰 재발급!!!");
                resultMap.put("access-token", accessToken);
                resultMap.put("message", SUCCESS);
                return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);
            }
        } else {
            logger.debug("refresh token 사용불가!!");
        }
        return new ResponseEntity<>(resultMap, HttpStatus.UNAUTHORIZED);
    }


    // 회원 상세 정보
    @ApiOperation(value = "회원상세 정보 보기")
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
    @PutMapping("/update")
    public ResponseEntity<Map<String, Object>> update(@RequestBody MemberDto memberDto) {
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
                resultMap.put("memberPw", findMemberPw);
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

    // 회원 최근 검색 등록
    @ApiOperation(value = "회원 최근 검색 등록")
    @PostMapping("/recent/{memberId}/{data}")
    public ResponseEntity<Map<String, Object>> insertRecentData(@PathVariable("memberId") String memberId, @PathVariable("data") String data) {
        logger.debug("member insert recent data controller, memberId : {}, data : {}", memberId, data);
        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            memberService.insertRecentData(memberId, data);
            logger.debug("최근 검색 등록 성공 ");
            resultMap.put("message", SUCCESS);
            return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            logger.error("최근 검색 등록 실패 : {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            return new ResponseEntity<>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // 회원 최근 검색
    @ApiOperation(value = "회원 최근 검색")
    @GetMapping("/recent/{memberId}")
    public ResponseEntity<Map<String, Object>> searchRecentData(@PathVariable("memberId") String memberId) {
        logger.debug("member search recent data controller, memberId : {}", memberId);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            List<RecentDto> recentDataList = memberService.searchRecentData(memberId);
            logger.debug("최근 검색 찾기 성공 : {}", recentDataList);
            resultMap.put("resultDataList", recentDataList);
            resultMap.put("message", SUCCESS);
            return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);

        } catch (Exception e) {
            logger.error("최근 검색 찾기 실패 : {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 회원 최근 검색 삭제
    @ApiOperation(value = "회원 최근 검색 삭제")
    @DeleteMapping("/recent/{memberId}/{recentId}")
    public ResponseEntity<Map<String, Object>> deleteRecentData(@PathVariable("memberId") String memberId, @PathVariable("recentId") String recentId) {
        logger.debug("member delete recent data controller, memberId : {}, recentId : {}", memberId, recentId);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            memberService.deleteRecentData(memberId, recentId);
            logger.debug("최근 검색 삭제 성공");
            resultMap.put("message", SUCCESS);
            return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            logger.error("최근 검색 삭제 실패 : {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            return new ResponseEntity<>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 회원 최근 검색 전체 삭제
    @ApiOperation(value = "회원 최근 검색 전체 삭제")
    @DeleteMapping("/recent/{memberId}")
    public ResponseEntity<Map<String, Object>> deleteRecentDataAll(@PathVariable("memberId") String memberId) {
        logger.debug("member delete recent data All controller, memberId : {}, recentId : {}", memberId);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            memberService.deleteRecentDataAll(memberId);
            logger.debug("최근 검색 전체 삭제 성공");
            resultMap.put("message", SUCCESS);
            return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            logger.error("최근 검색 전체 삭제 실패 : {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            return new ResponseEntity<>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

package com.ssafy.homepjt.controller;

import com.ssafy.homepjt.model.dto.BookmarkPathDto;
import com.ssafy.homepjt.model.dto.HouseInfoDto;
import com.ssafy.homepjt.model.request.BookmarkPathRequestDto;
import com.ssafy.homepjt.model.response.BookmarkPathResponseDto;
import com.ssafy.homepjt.model.service.BookmarkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bookmark")
@Api("장바구니 컨트롤러 API V1")
public class BookmarkController {

    public static final Logger logger = LoggerFactory.getLogger(BookmarkController.class);
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private BookmarkService bookmarkService;

    // 관심 상품 등록
    @ApiOperation(value = "관심 상품 등록")
    @PostMapping("/{memberId}/{aptCode}")
    public ResponseEntity<Map<String, Object>> insertBookmarkApt(@PathVariable("memberId") String memberId, @PathVariable("aptCode") long aptCode) {
        logger.debug("bookmark insert apt controller, memberId : {}, aptCode : {}", memberId, aptCode);
        Map<String, Object> resultMap = new HashMap<>();

        try {
            bookmarkService.insertBookmarkApt(memberId, aptCode);
            logger.debug("관심상품 등록 성공");
            resultMap.put("message", SUCCESS);
            return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            logger.error("관심상품 등록 실패");
            resultMap.put("message", e.getMessage());
            return new ResponseEntity<>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 관심 상품 목록 보기
    @ApiOperation(value = "관심 상품 조회")
    @GetMapping("/{memberId}")
    public ResponseEntity<Map<String, Object>> selectBookmarkApt(@PathVariable("memberId") String memberId) {
        logger.debug("bookmark select apt controller, memberId : {}", memberId);
        Map<String, Object> resultMap = new HashMap<>();

        try {
            List<HouseInfoDto> houseInfoDtoList = bookmarkService.selectBookmarkApt(memberId);
            logger.debug("관심상품 조회 성공");
            resultMap.put("houseInfoList", houseInfoDtoList);
            resultMap.put("message", SUCCESS);
            return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            logger.error("관심상품 조회 실패");
            resultMap.put("message", e.getMessage());
            return new ResponseEntity<>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 관심 상품 삭제
    @ApiOperation(value = "관심 상품 삭제")
    @DeleteMapping("/{memberId}/{aptCode}")
    public ResponseEntity<Map<String, Object>> deleteBookmarkApt(@PathVariable("memberId") String memberId, @PathVariable("aptCode") long aptCode) {
        logger.info("bookmark delete apt controller, memberId: {}, aptCode : {}", memberId, aptCode);
        Map<String, Object> resultMap = new HashMap<>();

        try {
            bookmarkService.deleteBookmarkApt(memberId, aptCode);
            logger.debug("관심상품 삭제 성공");
            resultMap.put("message", SUCCESS);
            return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            logger.error("관심상품 삭제 실패 : {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            return new ResponseEntity<>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 게시글 좋아요 회원 등록
    @ApiOperation(value = "게시글 좋아요 회원 등록")
    @PostMapping("/board/{boardId}/{memberId}")
    public ResponseEntity<Map<String, Object>> insertBookmarkMember(@PathVariable("boardId") int boardId, @PathVariable("memberId") String memberId) {
        logger.debug("bookmark insert board member controller, boardId : {}, memberId : {}", boardId, memberId);
        Map<String, Object> resultMap = new HashMap<>();

        try {
            bookmarkService.insertBookmarkMember(boardId, memberId);
            logger.debug("게시글 좋아요 회원 등록 성공");
            resultMap.put("message", SUCCESS);
            return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            logger.error("게시글 좋아요 회원 등록 실패");
            resultMap.put("message", e.getMessage());
            return new ResponseEntity<>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 게시글 좋아요 클릭 여부 확인
    @ApiOperation(value = "게시글 좋아요 클릭 여부 확인")
    @GetMapping("/board/check/{boardId}/{memberId}")
    public ResponseEntity<Map<String, Object>> checkBoardMemberLike(@PathVariable("boardId") int boardId, @PathVariable("memberId") String memberId) {
        logger.debug("bookmark check board member controller, boardId : {}, memberId : {}", boardId, memberId);
        Map<String, Object> resultMap = new HashMap<>();

        try {
            int clickCount = bookmarkService.checkBoardMemberLike(boardId, memberId);
            logger.debug("게시글 좋아요 클릭 여부 확인 성공");
            resultMap.put("message", SUCCESS);
            resultMap.put("clickCount", clickCount);
            return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            logger.error("게시글 좋아요 클릭 여부 확인 실패");
            resultMap.put("message", e.getMessage());
            return new ResponseEntity<>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 경로 리스트 출력
    @ApiOperation(value = "경로 리스트 출력")
    @PostMapping("/path/search")
    public ResponseEntity<Map<String, Object>> searchPath(@RequestBody List<BookmarkPathRequestDto> bookmarkPathRequestDtoList) {
        logger.debug("bookmark controller - bookmarkPathRequestDto : {}", bookmarkPathRequestDtoList);
        Map<String, Object> resultMap = new HashMap<>();

        try {

            Map<String, Object> result = bookmarkService.searchPath(bookmarkPathRequestDtoList);
            resultMap.put("timeArr", result.get("timeArr"));
            resultMap.put("distArr", result.get("distArr"));
            resultMap.put("timePointList", result.get("timePointList"));
            resultMap.put("distPointList", result.get("distPointList"));

            logger.debug("경로 탐색 성공");
            resultMap.put("message", SUCCESS);
            return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);

        } catch (Exception e) {
            logger.error("경로 탐색 실패 : {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            return new ResponseEntity<>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 관심 경로 리스트 출력(출발지, 도착지)
    @ApiOperation(value="관심 경로 리스트(출발지, 도착지)")
    @GetMapping("/path/{memberId}")
    public ResponseEntity<Map<String, Object>> searchBookmarkPath(@PathVariable("memberId") String memberId){
        logger.info("bookmark controller - memberId : {}", memberId);
        Map<String, Object> resultMap = new HashMap<>();

        try {
            List<BookmarkPathDto> bookmarkPathDtoList = bookmarkService.searchBookmarkPath(memberId);
            logger.info("관심 경로 탐색 성공 : {}", bookmarkPathDtoList);
            resultMap.put("success", SUCCESS);
            resultMap.put("bookmarkPathList", bookmarkPathDtoList);
            return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);
        }catch (Exception e){
            logger.error("관심 경로 탐색 실패 : {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            return new ResponseEntity<>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 관심 경로 리스트 출력(경유지)
    @ApiOperation(value = "관심 경로 리스트(경유지)")
    @GetMapping("/path/detail/{bookmarkPathId}")
    public ResponseEntity<Map<String, Object>> searchBookmarkPathDetail(@PathVariable("bookmarkPathId") int bookmarkPathId){
        logger.info("bookmark controller - bookmarkPathId : {]", bookmarkPathId);
        Map<String, Object> resultMap = new HashMap<>();

        try {
            List<BookmarkPathResponseDto> bookmarkPathResponseDtoList = bookmarkService.searchBookmarkPathDetail(bookmarkPathId);
            logger.info("관심 경로 탐색 성공 : {}", bookmarkPathResponseDtoList);
            resultMap.put("success", SUCCESS);
            resultMap.put("bookmarkPathDetailList", bookmarkPathResponseDtoList);
            return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);
        }catch (Exception e){
            logger.error("관심 경로 탐색 실패 : {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            return new ResponseEntity<>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 관심 경로 등록
    @ApiOperation(value = "관심 경로 등록")
    @PostMapping("/path/{memberId}/{pathName}")
    public ResponseEntity<Map<String, Object>> insertPath(@RequestBody List<BookmarkPathRequestDto> bookmarkPathRequestDtoList,
                                                          @PathVariable("memberId") String memberId, @PathVariable("pathName") String pathName) {
        logger.info("bookmark controller - bookmarkPathRequestDto : {}", bookmarkPathRequestDtoList);
        Map<String, Object> resultMap = new HashMap<>();

        try {
            bookmarkService.insertBookmarkPath(bookmarkPathRequestDtoList, memberId, pathName);
            logger.debug("관심 경로 등록 성공");
            resultMap.put("message", SUCCESS);
            return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            logger.error("관심 경로 등록 실패 : {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            return new ResponseEntity<>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 관심 경로 삭제
    @ApiOperation(value = "관심 경로 삭제")
    @DeleteMapping("/path/{bookmarkPathId}")
    public ResponseEntity<Map<String, Object>> deletePath(@PathVariable("bookmarkPathId") int bookmarkPathId) {
        logger.info("bookmark path delete controller, bookmarkPathId : {}", bookmarkPathId);
        Map<String, Object> resultMap = new HashMap<>();

        try {
            bookmarkService.deleteBookmarkPath(bookmarkPathId);
            logger.debug("관심 경로 삭제 성공");
            resultMap.put("message", SUCCESS);
            return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            logger.error("관심 경로 등록 실패 : {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            return new ResponseEntity<>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

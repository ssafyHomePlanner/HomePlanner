package com.ssafy.homepjt.controller;

import com.ssafy.homepjt.model.dto.HouseInfoDto;
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
    @DeleteMapping("/{bookmarkAptId}")
    public ResponseEntity<Map<String, Object>> deleteBookmarkApt(@PathVariable("bookmarkAptId") int bookmarkAptId) {
        logger.debug("bookmark delete apt controller, bookmarkAptId : {}", bookmarkAptId);
        Map<String, Object> resultMap = new HashMap<>();

        try {
            bookmarkService.deleteBookmarkApt(bookmarkAptId);
            logger.debug("관심상품 삭제 성공");
            resultMap.put("message", SUCCESS);
            return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            logger.error("관심상품 삭제 실패");
            resultMap.put("message", e.getMessage());
            return new ResponseEntity<>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 관심 경로 목록 보기
}

package com.ssafy.homepjt.controller;

import com.ssafy.homepjt.model.dto.DongCodeDto;
import com.ssafy.homepjt.model.service.DongCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dongcode")
@Api("동 코드 컨트롤러 API V1")
public class DongCodeController {

    public static final Logger logger = LoggerFactory.getLogger(DongCodeController.class);
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private DongCodeService dongCodeService;

    // 시도 이름 출력
    @ApiOperation(value = "시도 이름 출력")
    @GetMapping("")
    public ResponseEntity<Map<String, Object>> selectSidoName() {
        logger.info("dongcode sidoName controller");
        Map<String, Object> resultMap = new HashMap<>();

        try {
            List<String> sidoNameList = dongCodeService.selectSidoName();
            logger.debug("시도 이름 리스트 검색 확인 : {}", sidoNameList);
            resultMap.put("sidoNameList", sidoNameList);
            resultMap.put("message", SUCCESS);
            return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            logger.error("시도 이름 검색 실패 : {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            return new ResponseEntity<>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 시구군 이름 출력
    @ApiOperation(value = "시구군 이름 출력")
    @GetMapping("/gugun/{sidoName}")
    public ResponseEntity<Map<String, Object>> selectGugunName(@PathVariable("sidoName") String sidoName) {
        logger.info("dongcode gugunName controller");
        logger.info("sidoName : {}", sidoName);
        Map<String, Object> resultMap = new HashMap<>();

        try {
            List<String> gugunNameList = dongCodeService.selectGugunName(sidoName);
            logger.info("시구군 이름 리스트 검색 확인 : {}", gugunNameList);
            resultMap.put("gugunNameList", gugunNameList);
            resultMap.put("message", SUCCESS);
            return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            logger.error("시구군 이름 검색 실패: {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            return new ResponseEntity<>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 동 이름 출력
    @ApiOperation(value = "동 이름 출력")
    @GetMapping("/dong/{gugunName}")
    public ResponseEntity<Map<String, Object>> selectDongName(@PathVariable("gugunName") String gugunName) {
        logger.info("dongcode dongName controller");
        Map<String, Object> resultMap = new HashMap<>();

        try {
            List<String> dongNameList = dongCodeService.selectDongName(gugunName);
            logger.debug("동 이름 검색 성공 : {}", dongNameList);
            resultMap.put("dongNameList", dongNameList);
            resultMap.put("message", SUCCESS);
            return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            logger.error("동 이름 검색 실패 : {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            return new ResponseEntity<>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 해당 dongcode 시도, 시구군, 읍면동 출력
    @ApiOperation(value = "해당 동코드 정보 출력")
    @GetMapping("/info/{dongCode}")
    public ResponseEntity<Map<String, Object>> selectDongCodeInfo(@PathVariable("dongCode") String dongCode) {
        logger.info("dongcode info controller : {}", dongCode);
        Map<String, Object> resultMap = new HashMap<>();

        try {
            DongCodeDto dongCodeDto = dongCodeService.selectDongCodeInfo(dongCode);
            logger.info("동코드 정보 검색 성공 : {}", dongCodeDto);
            resultMap.put("dongCodeInfo", dongCodeDto);
            resultMap.put("message", SUCCESS);
            return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            logger.error("동코드 정보 검색 실패 : {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            return new ResponseEntity<>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

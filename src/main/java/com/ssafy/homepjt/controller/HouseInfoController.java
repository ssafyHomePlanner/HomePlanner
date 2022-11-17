package com.ssafy.homepjt.controller;

import com.ssafy.homepjt.model.dto.HouseDealDto;
import com.ssafy.homepjt.model.dto.HouseInfoDto;
import com.ssafy.homepjt.model.request.AptSearchRequestDto;
import com.ssafy.homepjt.model.service.HouseInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/house")
@Api("아파트 정보 컨트롤러 API V1")
public class HouseInfoController {

    public static final Logger logger = LoggerFactory.getLogger(MemberController.class);
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private HouseInfoService houseInfoService;

    // 아파트 검색
    @ApiOperation(value = "아파트 검색(시도, 구군, 동, 아파트 이름)")
    @PostMapping("{page}")
    public ResponseEntity<Map<String, Object>> selectHouseInfo(@RequestBody AptSearchRequestDto aptSearchRequestDto, @PathVariable("page") Optional<Integer> page) {

        if (!page.isPresent()) {
            page = Optional.of(1);
        }
        logger.info("houseinfo select houseinfo, requestDto : {}", aptSearchRequestDto);
        Map<String, Object> resultMap = new HashMap<>();

        try {
            Map<String, Object> result = houseInfoService.selectHouseInfo(aptSearchRequestDto, page.get().intValue());
            int startPage = (int) result.get("startPage");
            int endPage = (int) result.get("endPage");
            int currPage = (int) result.get("currPage");
            int totalPage = (int) result.get("totalPage");
            List<HouseInfoDto> houseInfoList = (List<HouseInfoDto>) result.get("houseInfoList");

            resultMap.put("startPage", startPage);
            resultMap.put("endPage", endPage);
            resultMap.put("currPage", currPage);
            resultMap.put("totalPage", totalPage);
            resultMap.put("houseInfoList", houseInfoList);

            logger.debug("아파트 검색 성공 : {}", houseInfoList);
            resultMap.put("message", SUCCESS);

            return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            logger.error("아파트 검색 실패 : {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            return new ResponseEntity<>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 아파트 조회수 증가
    @ApiOperation(value = "아파트 조회수 증가")
    @PutMapping("/update/view/{aptCode}")
    public ResponseEntity<Map<String, Object>> updateReadCount(@PathVariable("aptCode") String aptCode) {
        logger.info("house info update view count controller, aptCode : {}", aptCode);
        Map<String, Object> resultMap = new HashMap<>();

        try {
            houseInfoService.updateReadCount(aptCode);
            logger.debug("아파트 조회수 증가 성공");
            resultMap.put("message", SUCCESS);
            return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            logger.error("아파트 조회수 증가 실패");
            resultMap.put("message", e.getMessage());
            return new ResponseEntity<>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //아파트 검색(자동완성)
    @ApiOperation(value = "아파트 검색 자동완성")
    @PostMapping("/")
    public ResponseEntity<Map<String, Object>> selectHouseInfoAuto(@RequestParam("aptName") String aptName) {
        logger.info("houseinfo select houseinfo auto, aptName : {}", aptName);
        Map<String, Object> resultMap = new HashMap<>();

        try {
            List<HouseInfoDto> houseInfoDtoList = houseInfoService.selectHouseInfoAuto(aptName);
            logger.debug("아파트 검색 자동완성 성공 : {}", houseInfoDtoList);
            resultMap.put("message", SUCCESS);
            resultMap.put("houseInfoList", houseInfoDtoList);
            return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            logger.error("아파트 검색 자동완성 실패 : {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            return new ResponseEntity<>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 아파트 상세 검색(거래내역)
    @ApiOperation(value = "아파트 상세 검색(거래내역)")
    @GetMapping("/deal/{aptCode}")
    public ResponseEntity<Map<String, Object>> selectHouseDeal(@PathVariable("aptCode") String aptCode) {
        logger.info("house deal controller, aptCode: {}", aptCode);
        Map<String, Object> resultMap = new HashMap<>();

        try {
            List<HouseDealDto> houseDealList = houseInfoService.selectHouseDeal(aptCode);
            logger.debug("아파트 상세검색(거래내역) 성공 : {}", houseDealList);
            resultMap.put("message", SUCCESS);
            resultMap.put("houseDealList", houseDealList);
            return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            logger.error("아파트 상세 검색 실패 : {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            return new ResponseEntity<>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

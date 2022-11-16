package com.ssafy.homepjt.controller;

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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @PostMapping("")
    public ResponseEntity<Map<String, Object>> selectHouseInfo(@RequestBody AptSearchRequestDto aptSearchRequestDto) {
        logger.info("houseinfo select houseinfo, requestDto : {}", aptSearchRequestDto);
        Map<String, Object> resultMap = new HashMap<>();

        try {
            List<HouseInfoDto> houseInfoDtoList = houseInfoService.selectHouseInfo(aptSearchRequestDto);
            logger.debug("아파트 검색 성공 : {}", houseInfoDtoList);
            resultMap.put("message", SUCCESS);
            resultMap.put("houseInfoList", houseInfoDtoList);
            return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            logger.error("아파트 검색 실패 : {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            return new ResponseEntity<>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //아파트 검색(자동완성)
}

package com.ssafy.homepjt.controller;

import com.ssafy.homepjt.model.dto.PlannerDto;
import com.ssafy.homepjt.model.service.PlannerService;
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
@RequestMapping("/planner")
@Api("플래너 컨트롤러 API V1")
public class PlannerController {

    public static final Logger logger = LoggerFactory.getLogger(PlannerController.class);
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private PlannerService plannerService;

    @ApiOperation(value = "플래너 생성")
    @PostMapping("")
    public ResponseEntity<Map<String, Object>> writePlanner(@RequestBody PlannerDto plannerDto) {
        logger.info("planner write controller, plannerDto: {}", plannerDto);
        Map<String, Object> resultMap = new HashMap<>();

        try {
            plannerService.writePlanner(plannerDto);
            logger.debug("플래너 작성 성공");
            resultMap.put("message", SUCCESS);
            return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            logger.error("플래너 작성 실패 : {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            return new ResponseEntity<>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "플래너 조회")
    @GetMapping("/{memberId}")
    public ResponseEntity<Map<String, Object>> readPlanner(@PathVariable("memberId") String memberId) {
        logger.info("planner read controller, memberId : {}", memberId);
        Map<String, Object> resultMap = new HashMap<>();

        try {
            List<PlannerDto> plannerDtoList = plannerService.readPlanner(memberId);
            logger.debug("플래너 조회 성공, plannerList : {}", plannerDtoList);
            resultMap.put("plannerList", plannerDtoList);
            resultMap.put("success", SUCCESS);
            return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            logger.error("플래너 조회 실패 : {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            return new ResponseEntity<>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "플래너 수정")
    @PutMapping("")
    public ResponseEntity<Map<String, Object>> updatePlanner(@RequestBody PlannerDto plannerDto) {
        logger.info("planner update controller, plannerDto: {}", plannerDto);
        Map<String, Object> resultMap = new HashMap<>();

        try {
            plannerService.updatePlanner(plannerDto);
            logger.debug("플래너 수정 성공");
            resultMap.put("message", SUCCESS);
            return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            logger.error("플래너 수정 실패 : {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            return new ResponseEntity<>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "플래너 삭제")
    @DeleteMapping("/{plannerId}")
    public ResponseEntity<Map<String, Object>> deletePlanner(@PathVariable("plannerId") int plannerId) {
        logger.info("planner delete controller, plannerId : {}", plannerId);
        Map<String, Object> resultMap = new HashMap<>();

        try {
            plannerService.deletePlanner(plannerId);
            logger.debug("플래너 삭제 성공");
            resultMap.put("message", SUCCESS);
            return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            logger.error("플래너 삭제 실패 : {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            return new ResponseEntity<>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

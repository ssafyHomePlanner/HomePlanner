package com.ssafy.homepjt.model.service;

import com.ssafy.homepjt.model.dto.PlannerDto;

import java.util.List;

public interface PlannerService {
    // 플래너 생성
    public void writePlanner(PlannerDto plannerDto);

    // 플래너 조회
     public List<PlannerDto> readPlanner(String memberId);

    // 플래너 수정
    public void updatePlanner(PlannerDto plannerDto);

    // 플래너 삭제
    public void deletePlanner(int plannerId);
}

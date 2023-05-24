package com.ssafy.homepjt.model.service;

import com.ssafy.homepjt.model.dto.PlannerDto;
import com.ssafy.homepjt.model.mapper.PlannerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlannerServiceImpl implements PlannerService{

    @Autowired
    private PlannerMapper plannerMapper;

    @Override
    public void writePlanner(PlannerDto plannerDto) {
        plannerMapper.writePlanner(plannerDto);
    }

    @Override
    public List<PlannerDto> readPlanner(String memberId) {
        return plannerMapper.readPlanner(memberId);
    }

    @Override
    public void updatePlanner(PlannerDto plannerDto) {
        plannerMapper.updatePlanner(plannerDto);
    }

    @Override
    public void deletePlanner(int plannerId) {
        plannerMapper.deletePlanner(plannerId);
    }
}

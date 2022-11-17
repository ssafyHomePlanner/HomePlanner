package com.ssafy.homepjt.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PlannerDto {
    private int id;
    private String memberId;
    private long aptCode;
    private String aptName;
    private String aptAmount;
    private String hopedDate;
    private String expectedDate;
    private String budget;
    private String savingPerMonth;
    private String loanAmount;
    private String writeDate;
}

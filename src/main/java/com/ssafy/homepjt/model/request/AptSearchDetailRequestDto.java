package com.ssafy.homepjt.model.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AptSearchDetailRequestDto {
    private String aptName;
    private String sidoName;
    private String gugunName;
    private String dongName;
    private int minDealAmount;
    private int maxDealAmount;
    private int minArea;
    private int maxArea;
    private int minBuildYear;
    private int maxBuildYear;
}

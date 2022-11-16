package com.ssafy.homepjt.model.request;

import lombok.Data;

@Data
public class AptSearchRequestDto {
    private String sidoName;
    private String gugunName;
    private String dongName;
    private String aptName;
}

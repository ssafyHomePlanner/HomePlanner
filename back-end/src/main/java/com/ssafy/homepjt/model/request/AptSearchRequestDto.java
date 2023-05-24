package com.ssafy.homepjt.model.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AptSearchRequestDto {
    private String sidoName;
    private String gugunName;
    private String dongName;
    private String aptName;
}

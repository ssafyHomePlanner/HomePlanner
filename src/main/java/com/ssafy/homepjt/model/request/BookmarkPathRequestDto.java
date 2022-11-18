package com.ssafy.homepjt.model.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookmarkPathRequestDto {
    private long aptCode;
    private String aptName;
    private String lat;
    private String lon;
}

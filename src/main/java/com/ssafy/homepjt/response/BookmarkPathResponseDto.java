package com.ssafy.homepjt.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookmarkPathResponseDto {
    @JsonProperty
    private long aptCode;
    @JsonProperty
    private String aptName;
    @JsonProperty
    private String lat;
    @JsonProperty
    private String lon;
}
